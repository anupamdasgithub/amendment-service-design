#!/bin/bash
# ci-check.sh — CI gate for the amendments service repo.
# Run locally before committing, or wire into GitHub Actions (see
# .github/workflows/ci.yml).
#
#   1. Builds the service (compile only — fast fail on broken code)
#   2. Validates every BPMN file is well-formed and has a process definition
#   3. Validates every DMN file is well-formed and has a decision
#   4. Checks decision table rule arity matches declared inputs/outputs
#   5. Checks BPMN sequence flow references resolve to real nodes
#   6. Runs the decision unit tests
#
# Exit non-zero if anything fails, so it can gate a commit/push or pipeline.
set -uo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT"
FAIL=0

PROCESS_DIR="src/main/resources/processes"
DECISION_DIR="src/main/resources/decisions"

echo "=================================================="
echo " CI check — amendments service"
echo "=================================================="

# ---- 1. Build ----
echo ""
echo ">>> [1/6] Building service (mvn compile)"
if [ -f pom.xml ]; then
  if mvn -q -DskipTests compile; then
    echo "    PASS: service compiles"
  else
    echo "    FAIL: service did not compile"
    FAIL=1
  fi
else
  echo "    SKIP: pom.xml not found"
fi

# ---- 2. Validate BPMN ----
echo ""
echo ">>> [2/6] Validating BPMN files"
BPMN_FILES=$(find "$PROCESS_DIR" -name "*.bpmn" 2>/dev/null)
if [ -z "$BPMN_FILES" ]; then
  echo "    FAIL: no .bpmn files under $PROCESS_DIR"
  FAIL=1
else
  for f in $BPMN_FILES; do
    if ! python3 -c "import xml.etree.ElementTree as ET; ET.parse('$f')" 2>/dev/null; then
      echo "    FAIL: $f is not well-formed XML"
      FAIL=1
      continue
    fi
    if grep -q "bpmn2:process\|bpmn:process\|<process " "$f"; then
      echo "    PASS: $f"
    else
      echo "    FAIL: $f has no <process> definition"
      FAIL=1
    fi
  done
fi

# ---- 3. Validate DMN ----
echo ""
echo ">>> [3/6] Validating DMN files"
DMN_FILES=$(find "$DECISION_DIR" -name "*.dmn" 2>/dev/null)
if [ -z "$DMN_FILES" ]; then
  echo "    FAIL: no .dmn files under $DECISION_DIR"
  FAIL=1
else
  for f in $DMN_FILES; do
    if ! python3 -c "import xml.etree.ElementTree as ET; ET.parse('$f')" 2>/dev/null; then
      echo "    FAIL: $f is not well-formed XML"
      FAIL=1
      continue
    fi
    if grep -q "<decision " "$f"; then
      echo "    PASS: $f"
    else
      echo "    FAIL: $f has no <decision> definition"
      FAIL=1
    fi
  done
fi

# ---- 4. Decision table arity ----
# A rule with the wrong number of entries is valid XML but wrong logic:
# entries silently shift columns. Cheap to check, expensive to miss.
echo ""
echo ">>> [4/6] Checking decision table rule arity"
if ! python3 scripts/validate_dmn_arity.py "$DECISION_DIR"; then
  FAIL=1
fi

# ---- 5. BPMN reference integrity ----
# Catches a sequenceFlow pointing at a node id that does not exist, which
# the engine will only surface at build or deploy time.
echo ""
echo ">>> [5/6] Checking BPMN flow reference integrity"
if ! python3 scripts/validate_bpmn_refs.py "$PROCESS_DIR"; then
  FAIL=1
fi

# ---- 6. Decision tests ----
echo ""
echo ">>> [6/6] Running decision unit tests"
if mvn -q test -Dtest='*DecisionTest,*EligibilityTest'; then
  echo "    PASS: decision tests"
else
  echo "    FAIL: decision tests"
  FAIL=1
fi

echo ""
echo "=================================================="
if [ "$FAIL" -eq 0 ]; then
  echo " CI RESULT: PASS"
  echo "=================================================="
  exit 0
else
  echo " CI RESULT: FAIL"
  echo "=================================================="
  exit 1
fi

# Git Introduction — Gated Sequence

Run from the repo root.

The order is non-negotiable: **`.gitignore` must be in place and verified
BEFORE the first `git add`.** That is what guarantees secrets never enter
history. Verifying after the fact tells you whether you got lucky; verifying
before staging is what makes it deterministic.

This repo has already been through the sequence — the initial commit was
amended after Gate 2 caught a hole in the ignore rules. The gates below are
kept for the next contributor and for re-running before any push.

## Step 1 — init

```bash
git init
git branch -M main
```

## Step 2 — place repo files

Already in the repo:
- `.gitignore`
- `README.md`, `docs/adr/ADR-001-*.docx|pdf`
- `deploy/.env.template`
- `deploy/secrets/connector-secrets.txt.template`
- `scripts/ci-check.sh` and the two validators

## Step 3 — GATE 1: confirm secrets are ignored

`git status --ignored` collapses ignored directories, so it can hide a
per-file miss. Check explicitly:

```bash
for f in .env \
         deploy/secrets/connector-secrets.txt \
         src/main/resources/application-local.yaml \
         target/dummy.jar; do
  git check-ignore -q "$f" && echo "IGNORED  $f" || echo "TRACKED  $f"
done
```

Every one must report `IGNORED`. If any reports `TRACKED`, STOP and fix the
rule before proceeding.

## Step 4 — GATE 2: dry-run what would be staged

```bash
git add --dry-run . | grep -iE "secret|\.env|credential|token"
```

Expect **only** `.template` files. If a bare `.env` or a real
`connector-secrets.txt` appears, STOP.

> This gate is not theoretical. On the first run of this repo it caught
> `deploy/secrets/connector-secrets.txt` staging for commit — the original
> `.gitignore` covered `.env` and key extensions but had no rule for a
> secrets directory.

## Step 5 — stage and review

```bash
git add -A
git status
```

Confirm no real secret file, no `target/`, no `.bak`.

## Step 6 — commit

```bash
git commit -m "…"
```

## Step 7 — GATE 3: prove no secret entered history

```bash
git log --all --full-history --name-only --pretty=format: \
  -- '**/.env' '**/secrets/*' '**/*.p12' '**/*.jks' '**/*.pem' '**/*.key' \
     'application-local.*' \
  | grep -v '^$' | grep -v '\.template$'
```

**Empty output = success.** If a commit shows, the ignore failed: rotate the
exposed credentials immediately, then rebuild history — the file is in the
objects even after a later `git rm`.

## Step 8 — verify the build gate

```bash
./scripts/ci-check.sh
```

Model validation runs before the build, so a malformed decision table fails
in seconds rather than at code generation.

## Remote

```bash
git remote add origin <your-git-url>
git push -u origin main
```

Re-run Gate 3 before the first push. A private remote is still exposure —
the `.gitignore` is what keeps secrets local, not the repo visibility
setting.

## One git behaviour worth knowing

Do not ignore a secrets *directory* if you want to track templates inside it:

```gitignore
secrets/                      # WRONG — git will not descend into it,
!secrets/*.template           #         so this negation never applies

**/secrets/*                  # RIGHT — exclude the contents,
!**/secrets/*.template        #         negation is then evaluated
```

Git does not descend into an excluded directory, so negations for files
inside it are never evaluated. This repo uses the second form.

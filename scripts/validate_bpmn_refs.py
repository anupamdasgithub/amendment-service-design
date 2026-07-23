#!/usr/bin/env python3
"""Check every BPMN flow reference resolves to a node that exists.

A sequenceFlow pointing at a missing id, or an incoming/outgoing element
naming a flow that was renamed, is well-formed XML the engine will reject
at build or deploy time. Cheaper to catch here.
"""
import sys
import glob
import os
import xml.etree.ElementTree as ET

B = '{http://www.omg.org/spec/BPMN/20100524/MODEL}'


def check(path):
    root = ET.parse(path).getroot()

    ids = {el.get('id') for el in root.iter() if el.get('id')}
    failures = []

    for flow in root.iter(B + 'sequenceFlow'):
        for attr in ('sourceRef', 'targetRef'):
            value = flow.get(attr)
            if value not in ids:
                failures.append(f"sequenceFlow {flow.get('id')} {attr}='{value}' unresolved")

    for tag in ('incoming', 'outgoing'):
        for el in root.iter(B + tag):
            value = (el.text or '').strip()
            if value and value not in ids:
                failures.append(f"<{tag}>{value}</{tag}> unresolved")

    for boundary in root.iter(B + 'boundaryEvent'):
        attached = boundary.get('attachedToRef')
        if attached not in ids:
            failures.append(
                f"boundaryEvent {boundary.get('id')} attachedToRef='{attached}' unresolved")

    return failures


def main():
    target = sys.argv[1] if len(sys.argv) > 1 else 'src/main/resources/processes'
    files = sorted(glob.glob(os.path.join(target, '*.bpmn')))
    if not files:
        print(f"    FAIL: no .bpmn files found under {target}")
        return 1

    failed = 0
    for path in files:
        try:
            problems = check(path)
        except ET.ParseError as exc:
            print(f"    FAIL: {path} -> {exc}")
            failed = 1
            continue

        if problems:
            failed = 1
            for problem in problems:
                print(f"    FAIL: {path} {problem}")
        else:
            print(f"    PASS: {path}")

    return failed


if __name__ == '__main__':
    sys.exit(main())

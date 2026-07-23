#!/usr/bin/env python3
"""Check every DMN decision table rule has one entry per declared column.

A rule with too few or too many entries is well-formed XML but wrong logic:
the entries shift into neighbouring columns and the table evaluates against
the wrong inputs. The DMN editor prevents this; hand-edited XML does not.
"""
import sys
import glob
import os
import xml.etree.ElementTree as ET

NS = {'d': 'https://www.omg.org/spec/DMN/20191111/MODEL/'}
DT = '{https://www.omg.org/spec/DMN/20191111/MODEL/}decisionTable'


def check(path):
    failures = []
    root = ET.parse(path).getroot()
    for table in root.iter(DT):
        n_in = len(table.findall('d:input', NS))
        n_out = len(table.findall('d:output', NS))
        for rule in table.findall('d:rule', NS):
            got_in = len(rule.findall('d:inputEntry', NS))
            got_out = len(rule.findall('d:outputEntry', NS))
            if got_in != n_in or got_out != n_out:
                failures.append(
                    f"rule={rule.get('id')} inputs {got_in}/{n_in} outputs {got_out}/{n_out}")
    return failures


def main():
    target = sys.argv[1] if len(sys.argv) > 1 else 'src/main/resources/decisions'
    files = sorted(glob.glob(os.path.join(target, '*.dmn')))
    if not files:
        print(f"    FAIL: no .dmn files found under {target}")
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

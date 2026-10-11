#!/bin/bash

set -e

echo "Qodana Code Quality Brief Issues Report"

if [ -f "$1" ]; then
  ISSUE_COUNT=$(jq '.runs[0].results | length' "$1")

  if [ "$ISSUE_COUNT" -gt 0 ]; then
    jq -r '.runs[0].results[] |
      "Rule: \(.ruleId)\n" +
      "File: \(.locations[0].physicalLocation.artifactLocation.uri)\n" +
      "Line: \(.locations[0].physicalLocation.region.startLine)\n" +
      "Severity: \(.level // "unknown")\n" +
      "Message: \(.message.text)\n" +
      "-----------------------------------------" ' "$1"

    echo "Total Issues Found: $ISSUE_COUNT"
    exit 1
  fi

  echo "No issues found"
else
  echo "No issues found"
fi

exit 0

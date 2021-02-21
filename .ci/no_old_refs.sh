#!/bin/bash

set -e

# Script requires GITHUB_TOKEN env variable
MENTIONED_ISSUES=/tmp/mentioned_issues
CLOSED_ISSUES=/tmp/failed_issues
API_GITHUB_PREFIX="https://api.github.com/repos"
GITHUB_HOST="https://github.com"

# collect issues where full link is used
grep -IPohr "(after|[Tt]il[l]?) $GITHUB_HOST/[\w.-]+/[\w.-]+/issues/\d{1,5}" . \
  | sed -e 's/.*github.com\///' >> $MENTIONED_ISSUES

# collect checkstyle issues where only hash sign is used
grep -IPohr "[Tt]il[l]? #\d{1,5}" . \
  | sed -e 's/.*#/checkstyle\/checkstyle\/issues\//' >> $MENTIONED_ISSUES

for issue in $(sort -u $MENTIONED_ISSUES); do
  STATE=$(curl -s -H "Authorization: token $GITHUB_TOKEN" "$API_GITHUB_PREFIX/$issue" \
   | jq '.state' | xargs)
  if [ "$STATE" = "closed" ]; then
    echo "$GITHUB_HOST/$issue" >> $CLOSED_ISSUES
  fi
done

if [ -f "$CLOSED_ISSUES" ]; then
    echo "Following issues are mentioned in code to do something after they are closed:"
    cat $CLOSED_ISSUES
    exit 1
fi

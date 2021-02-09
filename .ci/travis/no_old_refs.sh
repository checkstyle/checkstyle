#!/bin/bash
# Attention, there is no "-x" to avoid problems on Travis
set -e

FAILED_IDS_FILE=failed
ISSUE_LINK_PREFIX="https://api.github.com/repos/checkstyle/checkstyle/issues/"

grep -Pohr "[Tt]il[l]? https://github.com/checkstyle/checkstyle/issues/\d{1,5}" ../.. \
| sed -e 's/.*issues\///' | sort | uniq > ids

while read issue_id; do
  STATE=$(curl -s -H "Authorization: token $READ_ONLY_TOKEN" "$ISSUE_LINK_PREFIX$issue_id" \
   | jq '.state')
  if [ "$STATE" = "closed" ]; then
    echo "https://github.com/checkstyle/checkstyle/issues/$issue_id" >> $FAILED_IDS_FILE
  fi
done < ids

if [ -f "$FAILED_IDS_FILE" ]; then
    echo "Following issues are mentioned in code to do something after they are closed:"
    cat $FAILED_IDS_FILE
    exit 1
fi

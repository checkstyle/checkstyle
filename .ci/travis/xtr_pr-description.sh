#!/bin/bash
# Attention, there is no "-x" to avoid problems on Travis
set -e

FAILED_IDS_FILE=failed
API_ISSUE_LINK_PREFIX="https://api.github.com/repos/checkstyle/checkstyle/issues"
ISSUE_LINK_PREFIX="https://github.com/checkstyle/checkstyle/issues"

grep -Pohr "[Tt]il[l]? $ISSUE_LINK_PREFIX/\d{1,5}" . \
| sed -e 's/.*issues\///' | sort | uniq > ids

while read issue_id; do
  STATE=$(curl -s -H "Authorization: token $READ_ONLY_TOKEN" "$API_ISSUE_LINK_PREFIX/$issue_id" \
   | jq '.state')
  echo "issue: $issue_id has state: $STATE"
  if [[ "$STATE" == "closed" ]]; then
    echo "$ISSUE_LINK_PREFIX/$issue_id" >> $FAILED_IDS_FILE
    cat $FAILED_IDS_FILE
  fi
done < ids

rm -f ids

if [ -f "$FAILED_IDS_FILE" ]; then
    echo "Following issues are mentioned in code to do something after they are closed:"
    cat $FAILED_IDS_FILE
    rm -f $FAILED_IDS_FILE
    exit 1
else
    echo "No such file"
fi

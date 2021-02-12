#!/bin/bash
# Attention, there is no "-x" to avoid problems on Travis
set -e

MENTIONED_IDS=mentioned_ids
CLOSED_ISSUES=failed
API_ISSUE_LINK_PREFIX="https://api.github.com/repos/checkstyle/checkstyle/issues"
ISSUE_LINK_PREFIX="https://github.com/checkstyle/checkstyle/issues"

grep -Pohr "(after|[Tt]il[l]?) $ISSUE_LINK_PREFIX/\d{1,5}" . \
  | sed -e 's/.*issues\///' | sort | uniq > $MENTIONED_IDS

while read issue_id; do
  STATE=$(curl -s -H "Authorization: token $READ_ONLY_TOKEN" "$API_ISSUE_LINK_PREFIX/$issue_id" \
   | jq '.state' | xargs)
  if [[ "$STATE" == "closed" ]]; then
    echo "$ISSUE_LINK_PREFIX/$issue_id" >> $CLOSED_ISSUES
  fi
done < $MENTIONED_IDS

rm -f $MENTIONED_IDS

if [ -f "$CLOSED_ISSUES" ]; then
    echo "Following issues are mentioned in code to do something after they are closed:"
    cat $CLOSED_ISSUES
    rm -f $CLOSED_ISSUES
    exit 1
fi


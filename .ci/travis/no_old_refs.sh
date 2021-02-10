#!/bin/bash
# Attention, there is no "-x" to avoid problems on Travis
set -e

MENTIONED_IDS=mentioned_ids
CLOSED_ISSIES=failed
API_ISSUE_LINK_PREFIX="https://api.github.com/repos/checkstyle/checkstyle/issues"
ISSUE_LINK_PREFIX="https://github.com/checkstyle/checkstyle/issues"

grep -Pohr "[Tt]il[l]? $ISSUE_LINK_PREFIX/\d{1,5}" . \
  | sed -e 's/.*issues\///' | sort | uniq > $MENTIONED_IDS

while read issue_id; do
  STATE=$(curl -s -H "Authorization: token $READ_ONLY_TOKEN" "$API_ISSUE_LINK_PREFIX/$issue_id" \
   | jq '.state' | xargs)
  if [[ "$STATE" == "closed" ]]; then
    echo "$ISSUE_LINK_PREFIX/$issue_id" >> $CLOSED_ISSIES
  fi
done < $MENTIONED_IDS

rm -f $MENTIONED_IDS

if [ -f "$CLOSED_ISSIES" ]; then
    echo "Following issues are mentioned in code to do something after they are closed:"
    cat $CLOSED_ISSIES
    rm -f $CLOSED_ISSIES
    exit 1
fi

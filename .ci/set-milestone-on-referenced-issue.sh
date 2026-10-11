#!/bin/bash

set -e

source ./.ci/util.sh

if [[ -z $1 ]]; then
  echo "[ERROR] Commits array is not set."
  echo "Usage: $BASH_SOURCE <commits array>"
  exit 1
fi

checkForVariable "GITHUB_TOKEN"

COMMITS_ARRAY=$1
echo "COMMITS_ARRAY=$COMMITS_ARRAY"

echo "Extracting commit messages from commits array that match '^(Pull|Issue) #[0-9]+'."
COMMIT_MESSAGES=$(echo "$COMMITS_ARRAY" | jq -r .[].message \
  | grep -E "^(Pull|Issue) #[0-9]+" || true)
echo "COMMIT_MESSAGES=
$COMMIT_MESSAGES"

if [ -z "$COMMIT_MESSAGES" ]; then
  echo "[WARN] No issue numbers found in commit messages."
  exit 0
fi

echo "Extracting issue numbers from commit messages."
ISSUE_NUMBERS=$(echo "$COMMIT_MESSAGES" | grep -oE "#[0-9]+" | cut -c2-)
echo "ISSUE_NUMBERS=
$ISSUE_NUMBERS"

if [ -z "$ISSUE_NUMBERS" ]; then
  echo "[ERROR] No issue numbers found in commit messages but was expecting some."
  exit 1
fi

echo "Fetching latest milestone."
MILESTONE=$(curl --fail-with-body -s \
  -H "Accept: application/vnd.github+json" \
  -H "Authorization: Bearer $GITHUB_TOKEN"\
  https://api.github.com/repos/checkstyle/checkstyle/milestones)
MILESTONE_NUMBER=$(echo "$MILESTONE" | jq .[0].number)
MILESTONE_TITLE=$(echo "$MILESTONE" | jq -r .[0].title)

if [ "$MILESTONE_NUMBER" == "null" ]; then
  echo "[ERROR] No milestone is found."
  exit 1
fi

echo "MILESTONE_NUMBER=$MILESTONE_NUMBER"
echo "MILESTONE_TITLE=$MILESTONE_TITLE"

function setMilestoneOnIssue {
  ISSUE_NUMBER=$1
  echo "Setting milestone $MILESTONE_TITLE to issue #$ISSUE_NUMBER."
  BODY="{\"milestone\": $MILESTONE_NUMBER}"
  curl --fail-with-body -s \
    -X PATCH \
    -H "Accept: application/vnd.github+json" \
    -H "Authorization: Bearer $GITHUB_TOKEN"\
    -d "$BODY" \
    https://api.github.com/repos/checkstyle/checkstyle/issues/"$ISSUE_NUMBER"
}

for ISSUE_NUMBER in $ISSUE_NUMBERS; do
  setMilestoneOnIssue "$ISSUE_NUMBER"
done

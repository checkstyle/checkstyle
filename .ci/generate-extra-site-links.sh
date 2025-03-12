#!/bin/bash

set -e

source ./.ci/util.sh

PR_NUMBER=$1
AWS_FOLDER_LINK=$2

if [[ -z $PR_NUMBER || -z $AWS_FOLDER_LINK ]]; then
  echo "not all parameters are set"
  echo "Usage: $BASH_SCRIPT <pull request number> <aws folder link>"
  exit 1
fi

checkForVariable "GITHUB_TOKEN"
checkForVariable "REPOSITORY_OWNER"
echo "PR_NUMBER=$PR_NUMBER"
echo "AWS_FOLDER_LINK=$AWS_FOLDER_LINK"

GITHUB_API_RESPONSE=$(curl --fail-with-body -Ls \
  -H "Accept: application/vnd.github+json" \
  -H "Authorization: Bearer $GITHUB_TOKEN" \
  "https://api.github.com/repos/$REPOSITORY_OWNER/checkstyle/pulls/$PR_NUMBER/files?per_page=100")
echo "GITHUB_API_RESPONSE=$GITHUB_API_RESPONSE"

# Extract a list of the changed xdocs in the pull request. For example 'xdoc/config_misc.xml'.
# We ignore template files and deleted files.
CHANGED_XDOCS_PATHS=$(echo "$GITHUB_API_RESPONSE" \
  | jq -r '.[] | select(.status != "removed") | .filename' \
  | grep src/site/xdoc/ \
  | grep -v '.*xml.template$' \
  || true)
echo "CHANGED_XDOCS_PATHS=$CHANGED_XDOCS_PATHS"

if [[ -z "$CHANGED_XDOCS_PATHS" ]]; then
  echo "[WARN] No xdocs were changed in the pull request."
  exit 0
fi

# Fetch the diff of the pull request.
PR_DIFF=$(curl --fail-with-body -s \
  "https://patch-diff.githubusercontent.com/raw/$REPOSITORY_OWNER/checkstyle/pull/$PR_NUMBER.diff")

# Iterate through all changed xdocs files.
while IFS= read -r CURRENT_XDOC_PATH
do
  # Extract the line number of the earliest change in the file, i.e. '90'.
  EARLIEST_CHANGE_LINE_NUMBER=$(echo "$PR_DIFF" | grep -A 5 "diff.*$CURRENT_XDOC_PATH" | grep @@ |
    head -1 | grep -oEi "[0-9]+" | head -1)
  # Add 3 to the number because diffs contain 3 lines of context.
  EARLIEST_CHANGE_LINE_NUMBER=$((EARLIEST_CHANGE_LINE_NUMBER + 3))
  echo "EARLIEST_CHANGE_LINE_NUMBER=$EARLIEST_CHANGE_LINE_NUMBER"

  # Find the id of the nearest subsection to the change.
  while read -r CURRENT_LINE
  do
    # When the line contains 'id='.
    if [[ $CURRENT_LINE =~ id\= ]]
    then
      # Extract the id value from the line.
      SUBSECTION_ID=$(echo "$CURRENT_LINE" | grep -Eo 'id="[^"]+"' | sed 's/id="\([^"]*\)"/\1/')
      echo "SUBSECTION_ID=$SUBSECTION_ID"
      break
    fi
  # Read the file from the earliest change to the top. It would read first row 90, then 89, 88..1.
  done < <(head -n "$EARLIEST_CHANGE_LINE_NUMBER" "$CURRENT_XDOC_PATH" | tac)

  # Extract file name from path, i.e. 'config_misc' and remove '.vm' if it exists.
  CURRENT_XDOC_NAME=$(echo "$CURRENT_XDOC_PATH" | sed 's/src\/site\/xdoc\/\(.*\)\.xml/\1/' \
    | sed 's/.vm//')
  echo "CURRENT_XDOC_NAME=$CURRENT_XDOC_NAME"

  echo "" >> .ci-temp/message # Add new line between each xdoc link.
  echo "$AWS_FOLDER_LINK/$CURRENT_XDOC_NAME.html#$SUBSECTION_ID" >> .ci-temp/message

  # Reset variable.
  SUBSECTION_ID=""
done <<< "$CHANGED_XDOCS_PATHS"
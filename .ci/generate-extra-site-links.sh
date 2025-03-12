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

  SUBSECTION_ID=""
  # Define diff context: from start of file to EARLIEST_CHANGE_LINE_NUMBER.
  DIFF_CONTEXT=$(head -n "$EARLIEST_CHANGE_LINE_NUMBER" "$CURRENT_XDOC_PATH" | tac)

  # First, try scanning the diff context for an explicit id.
  while read -r CURRENT_LINE
  do
    if [[ $CURRENT_LINE =~ id\= ]]; then
      SUBSECTION_ID=$(echo "$CURRENT_LINE" | grep -Eo 'id="[^"]+"' | sed 's/id="\([^"]*\)"/\1/')
      echo "SUBSECTION_ID found in diff context = $SUBSECTION_ID"
      break
    fi
  done <<< "$DIFF_CONTEXT"

  # If no id is found, fallback to the section's name attribute within the diff context.
  if [[ -z "$SUBSECTION_ID" ]]; then
    echo "No id found in diff context; checking for section name..."
    SECTION_NAME=$(echo "$DIFF_CONTEXT" | grep -Eo 'name="[^"]+"' | head -1 | sed 's/name="\([^"]*\)"/\1/')
    if [[ -n "$SECTION_NAME" ]]; then
      echo "SECTION_NAME found = $SECTION_NAME"
      # URL-encode the section name and replace % with . to match the expected id format.
      SUBSECTION_ID=$(python -c "import sys, urllib.parse; print(urllib.parse.quote(sys.argv[1]).replace('%','.'))" "$SECTION_NAME")
      echo "SUBSECTION_ID derived from section name = $SUBSECTION_ID"
    else
      echo "Warning: No id or section name found in diff context for $CURRENT_XDOC_PATH"
    fi
  fi

  # Extract file name from path, e.g., 'config_misc' (removing '.vm' if it exists).
  CURRENT_XDOC_NAME=$(echo "$CURRENT_XDOC_PATH" | sed 's/src\/site\/xdoc\/\(.*\)\.xml/\1/' | sed 's/.vm//')
  echo "CURRENT_XDOC_NAME=$CURRENT_XDOC_NAME"

  echo "" >> .ci-temp/message # Add new line between each xdoc link.
  echo "$AWS_FOLDER_LINK/$CURRENT_XDOC_NAME.html#$SUBSECTION_ID" >> .ci-temp/message

  # Reset variable.
  SUBSECTION_ID=""
done <<< "$CHANGED_XDOCS_PATHS"

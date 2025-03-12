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

# Fetch the list of modified files in the PR
echo "Fetching changed files from GitHub API..."
GITHUB_API_RESPONSE=$(curl --fail-with-body -Ls \
  -H "Accept: application/vnd.github+json" \
  -H "Authorization: Bearer $GITHUB_TOKEN" \
  "https://api.github.com/repos/$REPOSITORY_OWNER/checkstyle/pulls/$PR_NUMBER/files?per_page=100")

echo "GITHUB_API_RESPONSE: $GITHUB_API_RESPONSE"

# Extract a list of changed xdocs
CHANGED_XDOCS_PATHS=$(echo "$GITHUB_API_RESPONSE" | jq -r \
  '.[] | select(.status != "removed") | .filename' | grep src/site/xdoc/ | \
  grep -v '.*xml.template$' || true)

echo "Detected changed xdocs:"
echo "$CHANGED_XDOCS_PATHS"

if [[ -z "$CHANGED_XDOCS_PATHS" ]]; then
  echo "[WARN] No xdocs were changed in the pull request. Exiting..."
  exit 0
fi

# Fetch the diff of the pull request.
echo "Fetching PR diff..."
PR_DIFF=$(curl --fail-with-body -s \
  "https://patch-diff.githubusercontent.com/raw/$REPOSITORY_OWNER/checkstyle/pull/$PR_NUMBER.diff")

echo "PR_DIFF:"
echo "$PR_DIFF"

# Iterate through all changed xdoc files.
while IFS= read -r CURRENT_XDOC_PATH
do
  echo "Processing file: $CURRENT_XDOC_PATH"

  # Extract the line number of the earliest change in the file.
  EARLIEST_CHANGE_LINE_NUMBER=$(echo "$PR_DIFF" | grep -A 5 \
    "diff.*$CURRENT_XDOC_PATH" | grep @@ | head -1 | grep -oEi "[0-9]+" | \
    head -1)

  if [[ -z "$EARLIEST_CHANGE_LINE_NUMBER" ]]; then
    echo "[ERROR] Could not find line number for changes in $CURRENT_XDOC_PATH"
    continue
  fi

  # Add 3 to account for context lines in diff.
  EARLIEST_CHANGE_LINE_NUMBER=$((EARLIEST_CHANGE_LINE_NUMBER + 3))
  echo "EARLIEST_CHANGE_LINE_NUMBER=$EARLIEST_CHANGE_LINE_NUMBER"

  SUBSECTION_ID=""

  # Get the diff context from the beginning of the file up to the change line.
  DIFF_CONTEXT=$(head -n "$EARLIEST_CHANGE_LINE_NUMBER" "$CURRENT_XDOC_PATH" | tac)

  echo "DIFF_CONTEXT for $CURRENT_XDOC_PATH:"
  echo "$DIFF_CONTEXT"

  # First, try scanning for an explicit id.
  while read -r CURRENT_LINE
  do
    if [[ $CURRENT_LINE =~ id\= ]]; then
      SUBSECTION_ID=$(echo "$CURRENT_LINE" | grep -Eo 'id="[^"]+"' | sed 's/id="\([^"]*\)"/\1/')
      echo "SUBSECTION_ID found in diff context = $SUBSECTION_ID"
      break
    fi
  done <<< "$DIFF_CONTEXT"

  # If no id is found, fallback to the section's name attribute.
  if [[ -z "$SUBSECTION_ID" ]]; then
    echo "No id found in diff context; checking for section name..."
    SECTION_NAME=$(echo "$DIFF_CONTEXT" | grep -Eo 'name="[^"]+"' | \
      head -1 | sed 's/name="\([^"]*\)"/\1/')

    if [[ -n "$SECTION_NAME" ]]; then
      echo "SECTION_NAME found = $SECTION_NAME"
      # URL-encode the section name
      SUBSECTION_ID=$(python -c "import sys,urllib.parse; \
print(urllib.parse.quote(sys.argv[1]).replace('%','.'))" "$SECTION_NAME")
      echo "SUBSECTION_ID derived from section name = $SUBSECTION_ID"
    else
      echo "[WARN] No id or section name found for $CURRENT_XDOC_PATH"
    fi
  fi

  # Extract file name from path
  CURRENT_XDOC_NAME=$(echo "$CURRENT_XDOC_PATH" | sed 's/src\/site\/xdoc\/\(.*\)\.xml/\1/' |
   sed 's/.vm//')
  echo "CURRENT_XDOC_NAME=$CURRENT_XDOC_NAME"

  # Append the generated link to the message file
  echo "" >> .ci-temp/message
  echo "$AWS_FOLDER_LINK/$CURRENT_XDOC_NAME.html#$SUBSECTION_ID" >> .ci-temp/message
  echo "Added link: $AWS_FOLDER_LINK/$CURRENT_XDOC_NAME.html#$SUBSECTION_ID"

  # Reset variable for next iteration
  SUBSECTION_ID=""

done <<< "$CHANGED_XDOCS_PATHS"

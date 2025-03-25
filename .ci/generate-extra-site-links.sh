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
while IFS= read -r CURRENT_XDOC_PATH; do
  echo "Processing file: $CURRENT_XDOC_PATH"

  EARLIEST_CHANGE_LINE_NUMBER=$(
    echo "$PR_DIFF" | grep -A 5 "diff.*$CURRENT_XDOC_PATH" | grep @@ | \
    head -1 | grep -oEi "[0-9]+" | head -1
  )
  if [[ -n "$EARLIEST_CHANGE_LINE_NUMBER" ]]; then
    EARLIEST_CHANGE_LINE_NUMBER=$((EARLIEST_CHANGE_LINE_NUMBER + 3))
    echo "EARLIEST_CHANGE_LINE_NUMBER=$EARLIEST_CHANGE_LINE_NUMBER"
  else
    echo "No diff change for $CURRENT_XDOC_PATH; using diff context only."
  fi

  SUBSECTION_ID=""

  DIFF_CONTEXT=$(
    head -n "$EARLIEST_CHANGE_LINE_NUMBER" "$CURRENT_XDOC_PATH" | tac
  )
  echo "DIFF_CONTEXT:"
  echo "$DIFF_CONTEXT"

  # Extract name attribute only from section or subsection tags.
  SECTION_NAME=$(echo "$DIFF_CONTEXT" | \
    grep -oP '<(?:section|subsection)[^>]*name="\K[^"]+' | head -1)
  SECTION_NAME=$(echo "$SECTION_NAME" | tr -d '\n\r' | \
    sed 's/^[[:space:]]*//;s/[[:space:]]*$//')

  # Decode all HTML entities using Perl.
  SECTION_NAME=$(echo "$SECTION_NAME" | \
    perl -MHTML::Entities -pe '$_ = decode_entities($_)')

  echo "SECTION_NAME found: '$SECTION_NAME'"

  if [[ -n "$SECTION_NAME" ]]; then
    if echo "$SECTION_NAME" | grep -q '[^A-Za-z0-9 _-]'; then
      echo "Section name has special characters; encoding..."
      SUBSECTION_ID=$(echo "$SECTION_NAME" | tr -d '\n\r' | jq -sRr @uri | \
        sed 's/%/./g' | sed 's/\.20/_/g')
    else
      echo "Section name contains only allowed characters; replacing spaces..."
      SUBSECTION_ID=${SECTION_NAME// /_}
    fi

    # If the anchor starts with a digit, prefix it with an "a".
    if [[ "$SUBSECTION_ID" =~ ^[0-9] ]]; then
      SUBSECTION_ID="a$SUBSECTION_ID"
    fi
    echo "Derived SUBSECTION_ID: $SUBSECTION_ID"
  else
    echo "Warning: No section or subsection name found in $CURRENT_XDOC_PATH"
  fi

  CURRENT_XDOC_NAME=$(
    echo "$CURRENT_XDOC_PATH" | sed 's/src\/site\/xdoc\/\(.*\)\.xml/\1/' | \
    sed 's/.vm//'
  )
  echo "CURRENT_XDOC_NAME=$CURRENT_XDOC_NAME"

  echo "" >> .ci-temp/message
  echo "$AWS_FOLDER_LINK/$CURRENT_XDOC_NAME.html#$SUBSECTION_ID" \
    >> .ci-temp/message
  echo "Added link: $AWS_FOLDER_LINK/$CURRENT_XDOC_NAME.html#$SUBSECTION_ID"

  SUBSECTION_ID=""
done <<< "$CHANGED_XDOCS_PATHS"

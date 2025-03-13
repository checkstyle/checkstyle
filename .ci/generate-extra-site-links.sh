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

  EARLIEST_LINE=$(
    echo "$PR_DIFF" | grep -A 5 "diff.*$CURRENT_XDOC_PATH" | grep @@ | \
    head -1 | grep -oEi "[0-9]+" | head -1
  )
  if [[ -n "$EARLIEST_LINE" ]]; then
    EARLIEST_LINE=$((EARLIEST_LINE + 3))
    echo "EARLIEST_LINE=$EARLIEST_LINE"
  else
    echo "No diff change for $CURRENT_XDOC_PATH; using diff context only."
  fi

  DIFF_CONTEXT=$(
    head -n "$EARLIEST_LINE" "$CURRENT_XDOC_PATH" | tac
  )
  echo "DIFF_CONTEXT:"
  echo "$DIFF_CONTEXT"

  # Iterate over each diff hunk marker using process substitution.
  while IFS= read -r hunk; do
    NEW_START=$(echo "$hunk" | grep -oP "\+\K[0-9]+")
    if [[ -z "$NEW_START" ]]; then
      echo "Could not extract new start from hunk: $hunk"
      continue
    fi
    CONTEXT_LINE=$((NEW_START + 3))
    echo "Hunk new start: $NEW_START, context: $CONTEXT_LINE"

    HUNK_CONTEXT=$(
      head -n "$CONTEXT_LINE" "$CURRENT_XDOC_PATH" | tac
    )
    echo "HUNK_CONTEXT for hunk:"
    echo "$HUNK_CONTEXT"

    SUBSECTION_ID=$(
      echo "$HUNK_CONTEXT" | grep -Ei '<(subsection|section)' | \
      grep -Eo 'id\s*=\s*"[^"]+"' | head -1 | \
      sed 's/id\s*=\s*"\([^"]*\)"/\1/' | \
      sed 's/^[[:space:]]*//;s/[[:space:]]*$//'
    )
    if [[ -n "$SUBSECTION_ID" ]]; then
      echo "Found id: $SUBSECTION_ID"
      CURRENT_XDOC_NAME=$(
        echo "$CURRENT_XDOC_PATH" | sed 's/src\/site\/xdoc\/\(.*\)\.xml/\1/' | \
        sed 's/.vm//'
      )
      echo "$AWS_FOLDER_LINK/$CURRENT_XDOC_NAME.html#$SUBSECTION_ID" \
        >> .ci-temp/message
      echo "Added link: $AWS_FOLDER_LINK/$CURRENT_XDOC_NAME.html#$SUBSECTION_ID"
      SUBSECTION_ID=""
    else
      echo "No id found; checking for section name..."
      SECTION_NAME=$(
        echo "$HUNK_CONTEXT" | grep -Ei 'name\s*=\s*"[^"]+"' | head -1 | \
        sed 's/name\s*=\s*"\([^"]*\)"/\1/'
      )
      SECTION_NAME=$(echo "$SECTION_NAME" | sed \
        's/^[[:space:]]*//;s/[[:space:]]*$//')
      echo "SECTION_NAME found: '$SECTION_NAME'"
      if [[ -n "$SECTION_NAME" ]]; then
        if echo "$SECTION_NAME" | grep -q '[^A-Za-z0-9_-]'; then
          echo "Section name has special chars; encoding..."
          SUBSECTION_ID=$(python -c "import sys,urllib.parse; \
print(urllib.parse.quote(sys.argv[1]).replace('%','.'))" "$SECTION_NAME")
        else
          SUBSECTION_ID="$SECTION_NAME"
        fi
        echo "Derived SUBSECTION_ID: $SUBSECTION_ID"
        CURRENT_XDOC_NAME=$(
          echo "$CURRENT_XDOC_PATH" | sed 's/src\/site\/xdoc\/\(.*\)\.xml/\1/' | \
          sed 's/.vm//'
        )
        echo "$AWS_FOLDER_LINK/$CURRENT_XDOC_NAME.html#$SUBSECTION_ID" \
          >> .ci-temp/message
        echo "Added link: $AWS_FOLDER_LINK/$CURRENT_XDOC_NAME.html#$SUBSECTION_ID"
        SUBSECTION_ID=""
      else
        echo "Warning: No id or section name found in hunk of $CURRENT_XDOC_PATH"
      fi
    fi
  done < <(echo "$HUNK_MARKERS")

  # Add a newline after processing all hunks for this file.
  echo "" >> .ci-temp/message
done <<< "$CHANGED_XDOCS_PATHS"

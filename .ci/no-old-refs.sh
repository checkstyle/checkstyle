#!/bin/bash

set -e

# Script requires GITHUB_TOKEN env variable
MENTIONED_ISSUES_GREP_OUTPUT=/tmp/mentioned_issues_grep_output
CLOSED_ISSUES=/tmp/failed_issues

# Linked issues that are mentioned in the code
LINKED_ISSUES_MENTIONED=/tmp/linked_issues_mentioned
API_GITHUB_PREFIX="https://api.github.com/repos"
GITHUB_HOST="https://github.com"
MAIN_REPO="checkstyle/checkstyle"
DEFAULT_BRANCH="main"

# These are modified when event is of type pull_request
if [ -n "$PR_HEAD_REPO_NAME" ]; then
  MAIN_REPO=$PR_HEAD_REPO_NAME
  DEFAULT_BRANCH=$GITHUB_HEAD_REF
fi

# collect issues where full link is used
grep -IPonr "(after|[Tt]il[l]?) (<a href=\")?$GITHUB_HOST/[\w.-]+/[\w.-]+/issues/\d{1,5}" . \
  | sed -e 's/:[a-zA-Z].*github.com\//:/' >> $MENTIONED_ISSUES_GREP_OUTPUT

# collect checkstyle issues where only hash sign is used
grep -IPonr "[Tt]il[l]? #\d{1,5}" . \
  | sed -e 's/:[a-zA-Z].*#/:checkstyle\/checkstyle\/issues\//' >> $MENTIONED_ISSUES_GREP_OUTPUT

for MENTIONED_ISSUES_GREP_OUTPUT_LINE in $(cat $MENTIONED_ISSUES_GREP_OUTPUT); do
  ISSUE=${MENTIONED_ISSUES_GREP_OUTPUT_LINE#*[0-9]:}

  FILE_PATH=${MENTIONED_ISSUES_GREP_OUTPUT_LINE%:[0-9]*}
  FILE_PATH=${FILE_PATH:2}

  LINE_NUMBER=${MENTIONED_ISSUES_GREP_OUTPUT_LINE#*:}
  LINE_NUMBER=${LINE_NUMBER%:*}

  LINK="$GITHUB_HOST/$MAIN_REPO/blob/$DEFAULT_BRANCH/$FILE_PATH#L$LINE_NUMBER"

  STATE=$(curl --fail-with-body -s -H \
   "Authorization: token $GITHUB_TOKEN" "$API_GITHUB_PREFIX/$ISSUE" \
   | jq '.state' | xargs)
  if [ "$STATE" = "closed" ]; then
    echo "$LINK" >> $CLOSED_ISSUES
  fi
  if [ -n "$LINKED_ISSUES" ]; then
    for LINKED_ISSUE in $(cat "$LINKED_ISSUES"); do
      if [ "$LINKED_ISSUE" = "$GITHUB_HOST/$ISSUE" ]; then
        echo "$LINK" >> $LINKED_ISSUES_MENTIONED
      fi
    done
  fi
done

EXIT_CODE=0

if [ -f "$CLOSED_ISSUES" ]; then
    echo
    echo "Following lines are referencing closed issues."
    echo "Such lines should be removed or the issue should be examined:"
    echo
    cat $CLOSED_ISSUES
    EXIT_CODE=1
fi

if [ -f "$LINKED_ISSUES_MENTIONED" ]; then
    echo
    echo "Following lines are referencing to issues linked to this PR."
    echo "Such lines should be removed or the issue should be examined:"
    echo
    cat $LINKED_ISSUES_MENTIONED
    EXIT_CODE=1
fi

exit $EXIT_CODE

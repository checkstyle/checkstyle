#!/bin/bash
# Attention, there is no "-x" to avoid issues during execution
set -e

if [[ ! $PULL_REQUEST =~ ^([0-9]*)$ ]]; then exit 0; fi
LINK_COMMITS=https://api.github.com/repos/checkstyle/checkstyle/pulls/$PULL_REQUEST/commits
COMMITS=$(curl --fail-with-body -s -H "Authorization: token $READ_ONLY_TOKEN" "$LINK_COMMITS" \
             | jq '.[0] | .commit.message')
echo 'Commit messages from github: '"${COMMITS:0:60}"...
ISSUE_NUMBER=$(echo "$COMMITS" | sed -e 's/^.*Issue //' | sed -e 's/:.*//')
echo 'Issue number: '"$ISSUE_NUMBER" && RESULT=0
if [[ $ISSUE_NUMBER =~ ^#[0-9]+$ ]]; then
    LINK_PR=https://api.github.com/repos/checkstyle/checkstyle/pulls/$PULL_REQUEST
    LINK_ISSUE=https://api.github.com/repos/checkstyle/checkstyle/issues/${ISSUE_NUMBER:1}
    REGEXP=($ISSUE_NUMBER\|https://github.com/checkstyle/checkstyle/issues/${ISSUE_NUMBER:1})
    PR_DESC=$(curl --fail-with-body -s -H "Authorization: token $READ_ONLY_TOKEN" "$LINK_PR" \
                | jq '.body' | grep -E "$REGEXP" | cat )
    echo 'PR Description grepped:'"${PR_DESC:0:80}"
    if [[ -z $PR_DESC ]]; then
         echo 'Please put a reference to an Issue in the PR description,'
         echo 'this will bind the Issue to your PR in Github'
         RESULT=1;
       fi
    LABEL_APRV=$(curl --fail-with-body -s -H "Authorization: token $READ_ONLY_TOKEN" "$LINK_ISSUE" \
                   | jq '.labels [] | .name' | grep approved | cat | wc -l )
    if [[ $LABEL_APRV == 0 ]]; then
         echo 'You are providing a PR for an Issue that is not approved yet,'
         echo 'please ask admins to approve your Issue first'
         RESULT=1;
    fi
  fi
if [[ $RESULT == 0 ]]; then
      echo 'PR validation succeeded.';
else
      echo 'PR validation failed.' && false;
fi

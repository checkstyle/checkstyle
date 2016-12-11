#!/bin/bash
# Attention, there is no "-x" to avoid problems on Travis
set -e

if [[ ! $TRAVIS_PULL_REQUEST =~ ^([0-9]*)$ ]]; then exit 0; fi
LINK_COMMITS=https://api.github.com/repos/checkstyle/checkstyle/pulls/$TRAVIS_PULL_REQUEST/commits
COMMITS=$(curl -s -H "Authorization: token $GITHUB_AUTH_TOKEN" $LINK_COMMITS | jq '.[0] | .commit.message')
echo 'Messages from github:'${COMMITS:0:60}...
ISSUE_NUMBER=$(echo $COMMITS | sed -e 's/^.*Issue //' | sed -e 's/:.*//')
echo 'Issue number:'$ISSUE_NUMBER && RESULT=0
if [[ $ISSUE_NUMBER =~ ^#[0-9]+$ ]]; then
    LINK_PR=https://api.github.com/repos/checkstyle/checkstyle/pulls/$TRAVIS_PULL_REQUEST 
    LINK_ISSUE=https://api.github.com/repos/checkstyle/checkstyle/issues/${ISSUE_NUMBER:1}
    REGEXP=($ISSUE_NUMBER\|https://github.com/checkstyle/checkstyle/issues/${ISSUE_NUMBER:1})
    PR_DESC=$(curl -s -H "Authorization: token $GITHUB_AUTH_TOKEN" $LINK_PR | jq '.body' | grep -E $REGEXP | cat )
    echo 'PR Description grepped:'${PR_DESC:0:80} 
    if [[ -z $PR_DESC ]]; then
         echo 'Please put a reference of Issue to PR description, this will bind Issue and PR in Github' && RESULT=1;
       fi
    LABEL_APRV=$(curl -s -H "Authorization: token $GITHUB_AUTH_TOKEN" $LINK_ISSUE | jq '.labels [] | .name' | grep approved | cat | wc -l )
    if [[ $LABEL_APRV == 0 ]]; then
         echo 'You provide PR to Issue that is not approved yes, please ask admins to approve your issue first' && RESULT=1;
    fi
  fi
if [[ $RESULT == 0 ]]; then
      echo 'PR validation succeed.';
else
      echo 'PR validation failed.' && false;
fi

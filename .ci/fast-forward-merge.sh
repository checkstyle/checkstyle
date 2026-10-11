#!/usr/bin/env bash

# GitHub does not support fast-forward merge.
# This script is intended to simplify merge of Pull Requests to keep history linear (fast-forwarded)
# All Pull Requests from GitHub to our project has to be applied to our code by this script.

set -e

if [ $# -eq 0 ]
  then
    echo "$(basename "$0") FORK_USER_NAME USER_BRANCH
example:

    ./$(basename "$0") konstantinos issue73
"
    exit 0;
fi

GIT_REPO=checkstyle
FORK_USER_NAME=$1
USER_BRANCH=$2
REPO=${FORK_USER_NAME}-fork
LOCAL_USER_BRANCH=${FORK_USER_NAME}-${USER_BRANCH}

echo "removing remote ${REPO} if present ..."
git remote rm "${REPO}" | true

echo "adding remote ..."
git remote add "${REPO}" https://github.com/"${FORK_USER_NAME}"/${GIT_REPO}.git
git fetch "${REPO}"

echo "removing remote ${LOCAL_USER_BRANCH} if present ..."
git branch -D "${LOCAL_USER_BRANCH}" | true

echo "creating local branch ..."
git checkout -b "${LOCAL_USER_BRANCH}" "${REPO}"/"${USER_BRANCH}"

echo "rebasing over master ..."
git rebase master

echo "merge to master ..."
git checkout master
git merge "${LOCAL_USER_BRANCH}" --ff-only

echo "removing local branch ..."
git branch -D "${LOCAL_USER_BRANCH}"

echo "removing remote ..."
git remote rm "${REPO}"

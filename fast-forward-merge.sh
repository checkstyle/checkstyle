#!/usr/bin/env bash 

# Guthub does not support fast-forward merge.
# This scipt is intended to simplify merge of Pull Request to keep history liner (fast-forwarded)
# All Pull Request from Github to our project has to be applied to our code by this script.

set -e

if [ $# -eq 0 ]
  then
    echo "$(basename "$0") GIT_REPO FORK_USER_NAME USER_BRANCH
example:

    ./$(basename "$0") checkstyle konstantinos issue73
"
    exit 0;
fi

GIT_REPO=$1
FORK_USER_NAME=$2
USER_BRANCH=$3
REPO=${FORK_USER_NAME}-fork
LOCAL_USER_BRANCH=${FORK_USER_NAME}-${USER_BRANCH}

echo "adding remote ..."
git remote add ${REPO} https://github.com/${FORK_USER_NAME}/${GIT_REPO}.git
git fetch ${REPO}

echo "creating local branch ..."
git checkout -b ${LOCAL_USER_BRANCH} ${REPO}/${USER_BRANCH}

echo "rebasing over master ..."
git rebase master

echo "merge to master ..."
git checkout master
git merge ${LOCAL_USER_BRANCH} --ff-only

echo "removing local branch ..."
git branch -D ${LOCAL_USER_BRANCH}

echo "removing remote ..."
git remote rm ${REPO}

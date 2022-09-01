#!/usr/bin/env bash

set -e

source ./.ci/util.sh

checkForVariable "GITHUB_TOKEN"

if [[ -z $1 ]]; then
  echo "version is not set"
  echo "Usage: creation-of-issue-in-other-repos.sh <version>"
  exit 1
fi

TARGET_VERSION=$1
echo TARGET_VERSION="$TARGET_VERSION"

echo "Creation of issue in eclipse-cs repo ..."
curl -i -H "Authorization: token $GITHUB_TOKEN" \
  -d "{ \"title\": \"upgrade to checkstyle $TARGET_VERSION\", \
        \"body\": \"https://checkstyle.org/releasenotes.html#Release_$TARGET_VERSION\" \
        }" \
  -X POST https://api.github.com/repos/checkstyle/eclipse-cs/issues

echo "Creation of issue in sonar-checkstyle repo ..."
curl -i -H "Authorization: token $GITHUB_TOKEN" \
  -d "{ \"title\": \"upgrade to checkstyle $TARGET_VERSION\", \
        \"body\": \"https://checkstyle.org/releasenotes.html#Release_$TARGET_VERSION\" \
        }" \
  -X POST https://api.github.com/repos/checkstyle/sonar-checkstyle/issues

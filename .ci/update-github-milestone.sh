#!/bin/bash

set -e

source ./.ci/util.sh

if [[ -z $1 ]]; then
  echo "version is not set"
  echo "Usage: $BASH_SOURCE <version>"
  exit 1
fi

checkForVariable "GITHUB_TOKEN"

TARGET_VERSION=$1
echo TARGET_VERSION="$TARGET_VERSION"

echo "Updating milestone at github"
MILESTONE_ID=$(curl -s \
                -H "Authorization: token $GITHUB_TOKEN" \
                -X GET https://api.github.com/repos/checkstyle/checkstyle/milestones?state=open \
                | jq ".[0] | .number")

echo TARGET_VERSION="$TARGET_VERSION"
echo MILESTONE_ID="$MILESTONE_ID"

curl \
  -X PATCH https://api.github.com/repos/checkstyle/checkstyle/milestones/"$MILESTONE_ID" \
  -H "Authorization: token $GITHUB_TOKEN" \
  -d "{ \"title\": \"$TARGET_VERSION\" }"

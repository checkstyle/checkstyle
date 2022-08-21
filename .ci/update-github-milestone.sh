#!/bin/bash
set -e

source ./.ci/util.sh

checkForVariable "GITHUB_TOKEN"

VERSION=$1

echo "Updating milestone at github"
MILESTONE_ID=$(curl -s \
                -X GET https://api.github.com/repos/checkstyle/checkstyle/milestones?state=open \
                | jq ".[0] | .number")

echo VERSION="$VERSION"
echo MILESTONE_ID="$MILESTONE_ID"

curl \
  -X PATCH https://api.github.com/repos/checkstyle/checkstyle/milestones/"$MILESTONE_ID" \
  -H "Authorization: token $GITHUB_TOKEN" \
  -d "{ \"title\": \"$VERSION\" }"

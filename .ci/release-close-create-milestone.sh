#!/usr/bin/env bash

set -e

source ./.ci/util.sh

checkForVariable "GITHUB_TOKEN"
checkForVariable "REPOSITORY_OWNER"

echo "Close previous milestone at github"
MILESTONE_NUMBER=$(curl --fail-with-body -s \
                -H "Authorization: token $GITHUB_TOKEN" \
                -X GET https://api.github.com/repos/"$REPOSITORY_OWNER"/checkstyle/milestones?state=open \
                | jq ".[0] | .number")
curl --fail-with-body -i -H "Authorization: token $GITHUB_TOKEN" \
  -d "{ \"state\": \"closed\" }" \
  -X PATCH https://api.github.com/repos/"$REPOSITORY_OWNER"/checkstyle/milestones/"$MILESTONE_NUMBER"


echo "Creation of new milestone ..."

CURRENT_VERSION=$(getCheckstylePomVersionWithoutSnapshot)
echo CURRENT_VERSION="$CURRENT_VERSION"

LAST_SUNDAY_DAY=$(cal -d "$(date -d "next month" +"%Y-%m")" \
                    | awk '/^ *[0-9]/ { d=$1 } END { print d }')
LAST_SUNDAY_DATETIME=$(date -d "next month" +"%Y-%m")"-$LAST_SUNDAY_DAY""T08:00:00Z"
echo "$LAST_SUNDAY_DATETIME"

curl --fail-with-body -i -H "Authorization: token $GITHUB_TOKEN" \
  -d "{ \"title\": \"$CURRENT_VERSION\", \
        \"state\": \"open\", \
        \"description\": \"\", \
        \"due_on\": \"$LAST_SUNDAY_DATETIME\" \
        }" \
  -X POST https://api.github.com/repos/"$REPOSITORY_OWNER"/checkstyle/milestones

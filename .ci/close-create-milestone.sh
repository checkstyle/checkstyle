#!/usr/bin/env bash

set -e

source ./.ci/util.sh

checkForVariable "GITHUB_TOKEN"

echo "Close previous milestone at github"
MILESTONE_ID=$(curl --fail-with-body -s \
                -X GET https://api.github.com/repos/checkstyle/checkstyle/milestones?state=open \
                | jq ".[0] | .number")
curl --fail-with-body -i -H "Authorization: token $GITHUB_TOKEN" \
  -d "{ \"state\": \"closed\" }" \
  -X PATCH https://api.github.com/repos/checkstyle/checkstyle/milestones/"$MILESTONE_ID"


echo "Creation of new milestone ..."

CURRENT_VERSION=$(xmlstarlet sel -N pom=http://maven.apache.org/POM/4.0.0 \
                             -t -m pom:project -v pom:version pom.xml | sed "s/-SNAPSHOT//")
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
  -X POST https://api.github.com/repos/checkstyle/checkstyle/milestones

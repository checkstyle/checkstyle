#!/usr/bin/env bash

set -e

checkForVariable "GITHUB_TOKEN"

if [[ -z $1 ]]; then
  echo "version is not set"
  echo "Usage: update-github.sh <version>"
  exit 1
fi

TARGET_VERSION=$1
echo TARGET_VERSION="$TARGET_VERSION"

CURRENT_VERSION=$(xmlstarlet sel -N pom=http://maven.apache.org/POM/4.0.0 \
                             -t -m pom:project -v pom:version pom.xml | sed "s/-SNAPSHOT//")
echo CURRENT_VERSION="$CURRENT_VERSION"

if [ "$TARGET_VERSION" != "$CURRENT_VERSION" ]; then
  echo "[ERROR] Target Version and Current Version doesn't match."
  exit 1;
fi

echo "Updating milestone at github"
MILESTONE_ID=$(curl -s \
                -X GET https://api.github.com/repos/checkstyle/checkstyle/milestones?state=open \
                | jq ".[0] | .number")

echo TARGET_VERSION="$TARGET_VERSION"
echo MILESTONE_ID="$MILESTONE_ID"

curl \
  -X PATCH https://api.github.com/repos/checkstyle/checkstyle/milestones/"$MILESTONE_ID" \
  -H "Authorization: token $GITHUB_TOKEN" \
  -d "{ \"title\": \"$TARGET_VERSION\" }"

echo "Close previous milestone at github"
MILESTONE_ID=$(curl -s \
                -X GET https://api.github.com/repos/checkstyle/checkstyle/milestones?state=open \
                | jq ".[0] | .number")
curl -i -H "Authorization: token $GITHUB_TOKEN" \
  -d "{ \"state\": \"closed\" }" \
  -X PATCH https://api.github.com/repos/checkstyle/checkstyle/milestones/"$MILESTONE_ID"


echo "Creation of new milestone ..."
LAST_SUNDAY_DAY=$(cal -d "$(date -d "next month" +"%Y-%m")" \
                    | awk '/^ *[0-9]/ { d=$1 } END { print d }')
LAST_SUNDAY_DATETIME=$(date -d "next month" +"%Y-%m")"-$LAST_SUNDAY_DAY""T08:00:00Z"
echo "$LAST_SUNDAY_DATETIME"
curl -i -H "Authorization: token $GITHUB_TOKEN" \
  -d "{ \"title\": \"$TARGET_VERSION\", \
        \"state\": \"open\", \
        \"description\": \"\", \
        \"due_on\": \"$LAST_SUNDAY_DATETIME\" \
        }" \
  -X POST https://api.github.com/repos/checkstyle/checkstyle/milestones

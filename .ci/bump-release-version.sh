#!/bin/bash
set -e

source ./.ci/util.sh

CS_RELEASE_VERSION="$(getCheckstylePomVersion)"
git fetch --prune --unshallow
NEW_RELEASE=$(git describe --always --abbrev=0 | cut -d '-' -f 2)

echo CS_RELEASE_VERSION="$CS_RELEASE_VERSION"
echo NEW_RELEASE="$NEW_RELEASE"

SET_VERSION="$NEW_RELEASE-SNAPSHOT"
echo SET_VERSION="$SET_VERSION"

echo "bump version in pom.xml"
mvn -e --no-transfer-progress versions:set -DgroupId=com.puppycrawl.tools -DartifactId=checkstyle \
 -DoldVersion="${CS_RELEASE_VERSION}" -DnewVersion="${SET_VERSION}"
git push origin master

echo "Updating milestone at github"
MILESTONE_ID=$(curl -s \
                -X GET https://api.github.com/repos/Rahulkhinchi03/checkstyle/milestones?state=open \
                | jq ".[0] | .number")
curl \
  -X PATCH \
  -H "Authorization: token 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" \
  https://api.github.com/repos/Rahulkhinchi03/checkstyle/milestones/"$MILESTONE_ID" \
 -d "{ \"title\": \"$NEW_RELEASE\", \
        \"state\": \"open\", \
        \"description\": \"\" \
        }"

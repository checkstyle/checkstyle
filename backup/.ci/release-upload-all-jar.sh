#!/usr/bin/env bash

set -e

source ./.ci/util.sh

checkForVariable "GITHUB_TOKEN"
checkForVariable "REPOSITORY_OWNER"

if [[ -z $1 ]]; then
  echo "version is not set"
  echo "Usage: $BASH_SOURCE <version>"
  exit 1
fi

TARGET_VERSION=$1
echo TARGET_VERSION="$TARGET_VERSION"

git checkout checkstyle-"$TARGET_VERSION"

echo "Generating uber jar ...(no clean to keep site resources just in case)"
mvn -e --no-transfer-progress -Passembly,no-validations package

echo "Publishing 'all' jar to Github"
UPLOAD_LINK=https://uploads.github.com/repos/"$REPOSITORY_OWNER"/checkstyle/releases
RELEASE_ID=$(curl --fail-with-body -s -X GET \
  -H "Authorization: token $GITHUB_TOKEN" \
  https://api.github.com/repos/"$REPOSITORY_OWNER"/checkstyle/releases/tags/checkstyle-"$TARGET_VERSION" \
  | jq ".id")


curl --fail-with-body -i -H "Authorization: token $GITHUB_TOKEN" \
  -H "Content-Type: application/java-archive" \
  --data-binary @"target/checkstyle-$TARGET_VERSION-all.jar" \
  -X POST "$UPLOAD_LINK"/"$RELEASE_ID"/assets?name=checkstyle-"$TARGET_VERSION"-all.jar

echo "Jar Published for $TARGET_VERSION"

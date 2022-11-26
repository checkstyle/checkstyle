#!/usr/bin/env bash

set -e

source ./.ci/util.sh

checkForVariable "GITHUB_TOKEN"

if [[ -z $1 ]]; then
  echo "version is not set"
  echo "Usage: upload-all-jar.sh <version>"
  exit 1
fi

TARGET_VERSION=$1
echo TARGET_VERSION="$TARGET_VERSION"

git checkout checkstyle-"$TARGET_VERSION"

echo "Generating uber jar ...(no clean to keep site resources just in case)"
mvn -e --no-transfer-progress -Passembly package

echo "Publishing 'all' jar to Github"
UPLOAD_LINK=https://uploads.github.com/repos/checkstyle/checkstyle/releases
RELEASE_ID=$(curl -s -X GET \
  https://api.github.com/repos/checkstyle/checkstyle/releases/tags/checkstyle-"$TARGET_VERSION" \
  | jq ".id")


curl -i -H "Authorization: token $GITHUB_TOKEN" \
  -H "Content-Type: application/zip" \
  --data-binary @"target/checkout/target/checkstyle-$TARGET_VERSION-all.jar" \
  -X POST "$UPLOAD_LINK"/"$RELEASE_ID"/assets?name=checkstyle-"$TARGET_VERSION"-all.jar

echo "Jar Published for $TARGET_VERSION"

#!/usr/bin/env bash

set -e

source ./.ci/util.sh

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

./.ci/update-github-milestones.sh "$TARGET_VERSION"

UPLOAD_LINK=https://uploads.github.com/repos/checkstyle/checkstyle/releases

RELEASE_ID=$(curl -s -X GET \
  https://api.github.com/repos/checkstyle/checkstyle/releases/tags/checkstyle-"$TARGET_VERSION" \
  | jq ".id")

  echo "Publishing 'all' jar to Github"
  curl -i -H "Authorization: token $GITHUB_TOKEN" \
  -H "Content-Type: application/zip" \
  --data-binary @"target/checkout/target/checkstyle-$TARGET_VERSION-all.jar" \
  -X POST UPLOAD_LINK/"$RELEASE_ID"/assets?name=checkstyle-"$TARGET_VERSION"-all.jar

#!/usr/bin/env bash

set -e

if [[ -z $1 ]]; then
  echo "version is not set"
  echo "Usage: publish-releasenotes-githubpage.sh <version>"
  exit 1
fi

TARGET_VERSION=$1
echo TARGET_VERSION="$TARGET_VERSION"

CURRENT_VERSION=$(xmlstarlet sel -N pom=http://maven.apache.org/POM/4.0.0 \
                             -t -m pom:project -v pom:version pom.xml | sed "s/-SNAPSHOT//")
echo CURRENT_VERSION="$CURRENT_VERSION"

# Update GitHub Release Page
./.ci/update-github-page.sh "$TARGET_VERSION"

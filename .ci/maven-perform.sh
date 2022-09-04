#!/usr/bin/env bash

set -e

if [[ -z $1 ]]; then
  echo "version is not set"
  echo "Usage: maven-perform.sh <version>"
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

SKIP_CHECKSTYLE="-Dcheckstyle.ant.skip=true -Dcheckstyle.skip=true"

git checkout "checkstyle-$TARGET_VERSION"
echo "Deployment of jars to maven central (release:perform) ..."
mvn -e --no-transfer-progress release:perform -Darguments="$SKIP_CHECKSTYLE"

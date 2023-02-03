#!/bin/bash

set -e

if [[ -z $1 ]]; then
  echo "version is not set"
  echo "Usage: $BASH_SCRIPT <version>"
  exit 1
fi

TARGET_VERSION=$1
echo TARGET_VERSION="$TARGET_VERSION"

CURRENT_VERSION=$(xmlstarlet sel -N pom=http://maven.apache.org/POM/4.0.0 \
                             -t -m pom:project -v pom:version pom.xml | sed "s/-SNAPSHOT//")
echo CURRENT_VERSION="$CURRENT_VERSION"

if [ "$TARGET_VERSION" != "$CURRENT_VERSION" ]; then
  echo "Target Version and current Version do not match"
  exit 1;
fi

SKIP_TEST="-DskipTests -DskipITs"
SKIP_CHECKSTYLE="-Dcheckstyle.ant.skip=true -Dcheckstyle.skip=true"
SKIP_OTHERS="-Dpmd.skip=true -Dspotbugs.skip=true -Djacoco.skip=true -Dxml.skip=true"

git checkout "checkstyle-$TARGET_VERSION"
echo "Deploying jars to maven central (release:perform) ..."
mvn -e --no-transfer-progress -Pgpg release:perform \
  -DconnectionUrl=scm:git:git@github.com:checkstyle/checkstyle.git \
  -Dtag=checkstyle-"$TARGET_VERSION" \
  -Darguments="$SKIP_TEST $SKIP_CHECKSTYLE $SKIP_OTHERS"

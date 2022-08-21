#!/usr/bin/env bash

set -e

if [[ -z $1 ]]; then
  echo "latest version is not set"
  exit 1
fi
LATEST_VERSION=$1
echo LATEST_VERSION="$LATEST_VERSION"

POM_FILE=pom.xml
CURRENT_VERSION=$(grep "^  <version>.*</version>$" $POM_FILE | awk -F'[><]' '{print $3}')
echo CURRENT_VERSION="$CURRENT_VERSION"

if [ "$LATEST_VERSION" != "$CURRENT_VERSION" ]; then
  mvn -e --no-transfer-progress versions:set \
    -DgroupId=com.puppycrawl.tools -DartifactId=checkstyle -DnewVersion="$LATEST_VERSION-SNAPSHOT"
  echo "Version updated to $1-SNAPSHOT"
fi

SKIP_TEST="-DskipTests -DskipITs"
SKIP_CHECKSTYLE="-Dcheckstyle.ant.skip=true -Dcheckstyle.skip=true"
SKIP_OTHERS="-Dpmd.skip=true -Dspotbugs.skip=true -Djacoco.skip=true -Dxml.skip=true"

echo "Version bump in pom.xml (release:prepare) ..."
mvn -e --no-transfer-progress -Pgpg release:prepare -B -Darguments="$SKIP_TEST $SKIP_CHECKSTYLE \
  $SKIP_OTHERS"


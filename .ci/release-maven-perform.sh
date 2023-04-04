#!/bin/bash

set -e

source ./.ci/util.sh

if [[ -z $1 ]]; then
  echo "version is not set"
  echo "Usage: $BASH_SCRIPT <version>"
  exit 1
fi

checkForVariable "REPOSITORY_OWNER"

TARGET_VERSION=$1
echo TARGET_VERSION="$TARGET_VERSION"

SKIP_TEST="-DskipTests -DskipITs"
SKIP_CHECKSTYLE="-Dcheckstyle.ant.skip=true -Dcheckstyle.skip=true"
SKIP_OTHERS="-Dpmd.skip=true -Dspotbugs.skip=true -Djacoco.skip=true -Dxml.skip=true \
  --no-transfer-progress"

git checkout "checkstyle-$TARGET_VERSION"
echo "Deploying jars to maven central (release:perform) ..."
mvn -e --no-transfer-progress -Pgpg -Pgpgv2 release:perform \
  -DconnectionUrl=scm:git:https://github.com/"$REPOSITORY_OWNER"/checkstyle.git \
  -Dtag=checkstyle-"$TARGET_VERSION" \
  -Darguments="$SKIP_TEST $SKIP_CHECKSTYLE $SKIP_OTHERS"

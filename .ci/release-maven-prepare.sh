#!/usr/bin/env bash

set -e

source ./.ci/util.sh

if [[ -z $1 ]]; then
  echo "version is not set"
  echo "Usage: $BASH_SOURCE <version>"
  exit 1
fi

TARGET_VERSION=$1
echo TARGET_VERSION="$TARGET_VERSION"

CURRENT_VERSION=$(getCheckstylePomVersionWithoutSnapshot)
echo CURRENT_VERSION="$CURRENT_VERSION"

if [ "$TARGET_VERSION" != "$CURRENT_VERSION" ]; then
  echo "[ERROR] Target Version and Current Version doesn't match."
  exit 1;
fi

if [[ $(grep "<section name=\"Release $TARGET_VERSION\">" src/xdocs/releasenotes.xml \
           | cat | wc -l) -eq 0 ]]; then
  echo "src/xdocs/releasenotes.xml do not have section for $TARGET_VERSION"
  exit 1
fi

SKIP_TEST="-DskipTests -DskipITs"
SKIP_CHECKSTYLE="-Dcheckstyle.ant.skip=true -Dcheckstyle.skip=true"
SKIP_OTHERS="-Dpmd.skip=true -Dspotbugs.skip=true -Djacoco.skip=true -Dxml.skip=true -Dgpg.skip"

echo "Version bump in pom.xml (release:prepare) ..."
mvn -e --no-transfer-progress release:prepare -B -DpushChanges=false \
    -Darguments="$SKIP_TEST $SKIP_CHECKSTYLE $SKIP_OTHERS"

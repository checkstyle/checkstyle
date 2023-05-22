#!/bin/bash
set -e

source ./.ci/util.sh

if [[ -z $1 ]]; then
  echo "version is not set"
  echo "Usage: $BASH_SOURCE <version>"
  exit 1
fi

NEW_VERSION=$1
echo NEW_VERSION="$NEW_VERSION"

if ! [[ "$NEW_VERSION" =~ ^[0-9]+\.[0-9]+\.[0-9]+$ ]]; then
  echo "[ERROR] New version($NEW_VERSION) is not in the format of <major>.<minor>.<patch>"
  exit 1;
fi

CURRENT_VERSION=$(getCheckstylePomVersionWithoutSnapshot)
echo CURRENT_VERSION="$CURRENT_VERSION"

if [ "$NEW_VERSION" == "$CURRENT_VERSION" ]; then
  echo "[ERROR] New Version and Current Version are the same."
  exit 1;
fi

CURRENT_VERSION_FIRST_NUMBER=$(echo "$CURRENT_VERSION" | cut -d "." -f 1)
CURRENT_VERSION_SECOND_NUMBER=$(echo "$CURRENT_VERSION" | cut -d "." -f 2)
CURRENT_VERSION_THIRD_NUMBER=$(echo "$CURRENT_VERSION" | cut -d "." -f 3)

NEW_VERSION_FIRST_NUMBER=$(echo "$NEW_VERSION" | cut -d "." -f 1)
NEW_VERSION_SECOND_NUMBER=$(echo "$NEW_VERSION" | cut -d "." -f 2)
NEW_VERSION_THIRD_NUMBER=$(echo "$NEW_VERSION" | cut -d "." -f 3)

function assertNumberIsIncrementedByOne {
  NEW_NUMBER=$1
  CURRENT_NUMBER=$2
  NUMBER_TYPE=$3
  DIFFERENCE="$(($NEW_NUMBER-$CURRENT_NUMBER))"
  if [ "$DIFFERENCE" -ne 1 ]; then
    echo "[ERROR] $NUMBER_TYPE number($CURRENT_NUMBER) has changed to" \
      "$NEW_NUMBER but it should be incremented by max 1."
    exit 1;
  fi
}

if [ "$CURRENT_VERSION_FIRST_NUMBER" != "$NEW_VERSION_FIRST_NUMBER" ]; then
  assertNumberIsIncrementedByOne "$NEW_VERSION_FIRST_NUMBER" "$CURRENT_VERSION_FIRST_NUMBER" "Major"

  if [ "$NEW_VERSION_SECOND_NUMBER" -ne 0 ] || [ "$NEW_VERSION_THIRD_NUMBER" -ne 0 ]; then
    echo "[ERROR] Minor and patch number should be 0 when major number is bumped."
    exit 1;
  fi
elif [ "$CURRENT_VERSION_SECOND_NUMBER" != "$NEW_VERSION_SECOND_NUMBER" ]; then
  assertNumberIsIncrementedByOne "$NEW_VERSION_SECOND_NUMBER" \
    "$CURRENT_VERSION_SECOND_NUMBER" "Minor"

  if [ "$NEW_VERSION_THIRD_NUMBER" -ne 0 ]; then
    echo "[ERROR] Patch number should be 0 when minor number is bumped."
    exit 1;
  fi
elif [ "$CURRENT_VERSION_THIRD_NUMBER" != "$NEW_VERSION_THIRD_NUMBER" ]; then
  assertNumberIsIncrementedByOne "$NEW_VERSION_THIRD_NUMBER" "$CURRENT_VERSION_THIRD_NUMBER" "Patch"
fi

echo "bump version in pom.xml"
mvn -e --no-transfer-progress versions:set -DnewVersion="$NEW_VERSION-SNAPSHOT"
mvn -e --no-transfer-progress versions:commit

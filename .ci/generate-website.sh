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

git checkout "checkstyle-$TARGET_VERSION"

echo "Generating web site"
./mvnw -e --no-transfer-progress site -Pno-validations \
  -Dmaven.javadoc.skip=false -Djdepend.skip=false

git checkout origin/master

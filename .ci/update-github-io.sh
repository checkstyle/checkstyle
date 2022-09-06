#!/usr/bin/env bash

set -e

source ./.ci/util.sh

if [[ -z $1 ]]; then
  echo "version is not set"
  echo "Usage: update-github-io.sh <version>"
  exit 1
fi

TARGET_VERSION=$1
echo TARGET_VERSION="$TARGET_VERSION"

git checkout "checkstyle-$TARGET_VERSION"

echo "Generating web site"
mvn -e --no-transfer-progress site -Pno-validations -Dmaven.javadoc.skip=false

mkdir .ci-temp

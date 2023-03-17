#!/bin/bash
set -e

source ./.ci/util.sh

if [[ -z $1 ]]; then
  echo "version is not set"
  echo "Usage: $BASH_SOURCE <version>"
  exit 1
fi

TARGET_VERSION=$1
echo TARGET_VERSION="$TARGET_VERSION"

cd .ci-temp/checkstyle.github.io/
git config user.name 'github-actions[bot]'
git config user.email 'github-actions[bot]@users.noreply.github.com'
git rm -rf *
git checkout HEAD -- CNAME
cp -R ../../target/site/* .
git add .
git commit -m "release $TARGET_VERSION"
echo "Push site content to remote ..."
echo "We do force to avoid history changes, we do not need history as github.io shows only HEAD."
git push origin --force

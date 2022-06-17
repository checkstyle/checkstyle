#!/bin/bash

set -e

source ./.ci/util.sh

echo "PULL_REQUEST:""$PULL_REQUEST"
if [[ $PULL_REQUEST =~ ^([0-9]+)$ ]]; then
  echo "Build is not for Pull Request";
  sleep 5;
  exit 0;
fi

checkout_from https://github.com/checkstyle/contribution
cd .ci-temp/contribution/releasenotes-builder
mvn -e --no-transfer-progress clean compile package
cd ../../../

if [ -d .ci-temp/checkstyle ]; then
  cd .ci-temp/checkstyle/
  git reset --hard origin/master
  git pull origin master
  git fetch --tags
  cd ../../
else
  cd .ci-temp/
  git clone https://github.com/checkstyle/checkstyle
  cd ../
fi

CS_RELEASE_VERSION="$(getCheckstylePomVersion)"
echo CS_RELEASE_VERSION="$CS_RELEASE_VERSION"

cd .ci-temp/checkstyle
LATEST_RELEASE_TAG=$(git describe --tags --abbrev=0)
echo LATEST_RELEASE_TAG="$LATEST_RELEASE_TAG"

cd ../
NotesDir="contribution/releasenotes-builder/src/main/resources/com/github/checkstyle/templates/"

java -jar contribution/releasenotes-builder/target/releasenotes-builder-1.0-all.jar \
        -localRepoPath checkstyle -remoteRepoPath checkstyle/checkstyle \
        -startRef "$LATEST_RELEASE_TAG" -releaseNumber "$CS_RELEASE_VERSION" \
        -githubAuthToken "$READ_ONLY_TOKEN" -generateXdoc \
        -xdocTemplate $NotesDir/xdoc_freemarker.template -publishXdoc -publishXdocWithPush

echo "releasenotes.xml after commit:"
head "checkstyle/src/xdocs/releasenotes.xml" -n 100

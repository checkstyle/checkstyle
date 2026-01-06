#!/bin/bash
set -e

source ./.ci/util.sh
checkForVariable "READ_ONLY_TOKEN"

if [[ -z $1 ]]; then
  echo "version is not set"
  echo "Usage: $BASH_SOURCE <version>"
  exit 1
fi

TARGET_VERSION=$1
echo TARGET_VERSION="$TARGET_VERSION"

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

cd .ci-temp/checkstyle
LATEST_RELEASE_TAG=$(curl --fail-with-body -s -H "Authorization: token $READ_ONLY_TOKEN" \
                       https://api.github.com/repos/checkstyle/checkstyle/releases/latest \
                       | jq ".tag_name")
echo LATEST_RELEASE_TAG="$LATEST_RELEASE_TAG"

cd ../

BUILDER_RESOURCE_DIR="contribution/releasenotes-builder/src/main/resources/com/github/checkstyle"

java -jar contribution/releasenotes-builder/target/releasenotes-builder-1.0-all.jar \
     -localRepoPath checkstyle \
     -remoteRepoPath checkstyle/checkstyle \
     -startRef "$LATEST_RELEASE_TAG" \
     -releaseNumber "$TARGET_VERSION" \
     -githubAuthToken "$READ_ONLY_TOKEN" \
     -generateXdoc \
     -xdocTemplate $BUILDER_RESOURCE_DIR/templates/xdoc_freemarker.template

cd ../
sed -i '12r .ci-temp/xdoc.xml' src/site/xdoc/releasenotes.xml

echo "releasenotes.xml:"
head "src/site/xdoc/releasenotes.xml" -n 100

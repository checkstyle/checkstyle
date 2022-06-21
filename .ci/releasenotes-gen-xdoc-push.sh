#!/bin/bash
set -e

source ./.ci/util.sh

checkForVariable() {
  if [[ "$(declare -p "$1")" =~ ^declare\ -x ]]; then
    echo "Error: Define $1 environment variable"
    exit 1
  fi
}

checkForVariable "READ_ONLY_TOKEN"

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
LATEST_RELEASE_TAG=$(curl -s https://api.github.com/repos/checkstyle/checkstyle/releases/latest \
| jq .. .tag_name? // empty)
echo LATEST_RELEASE_TAG="$LATEST_RELEASE_TAG"

cd ../

RELEASENOTES_BUILDER="contribution/releasenotes-builder"
TEMPLATE_DIR="/src/main/resources/com/github/checkstyle/templates/"

java -jar $RELEASENOTES_BUILDER/target/releasenotes-builder-1.0-all.jar \
     -localRepoPath checkstyle \
     -remoteRepoPath checkstyle/checkstyle \
     -startRef "$LATEST_RELEASE_TAG" \
     -releaseNumber "$CS_RELEASE_VERSION" \
     -githubAuthToken "$READ_ONLY_TOKEN" \
     -generateXdoc \
     -xdocTemplate $RELEASENOTES_BUILDER/$TEMPLATE_DIR/xdoc_freemarker.template \
     -publishXdoc -publishXdocWithPush

echo "releasenotes.xml after commit:"
head "checkstyle/src/xdocs/releasenotes.xml" -n 100

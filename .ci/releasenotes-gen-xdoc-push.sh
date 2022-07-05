#!/bin/bash
set -e

source ./.ci/util.sh

checkForVariable() {
  VAR_NAME=$1
  if [ ! -v "$VAR_NAME" ]; then
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
                       | jq ".tag_name")
echo LATEST_RELEASE_TAG="$LATEST_RELEASE_TAG"

cd ../

BUILDER_RESOURCE_DIR="contribution/releasenotes-builder/src/main/resources/com/github/checkstyle"

java -jar contribution/releasenotes-builder/target/releasenotes-builder-1.0-all.jar \
     -localRepoPath checkstyle \
     -remoteRepoPath checkstyle/checkstyle \
     -startRef "$LATEST_RELEASE_TAG" \
     -releaseNumber "$CS_RELEASE_VERSION" \
     -githubAuthToken "$READ_ONLY_TOKEN" \
     -generateXdoc \
     -xdocTemplate $BUILDER_RESOURCE_DIR/templates/xdoc_freemarker.template \

cd ../

CS_RELEASE_VERSION=$(mvn -e --no-transfer-progress -q -Dexec.executable='echo' \
              -Dexec.args='${project.version}' \
              --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec | sed 's/-SNAPSHOT//')
  
LATEST_RELEASE_TAG=$(curl -s https://api.github.com/repos/checkstyle/checkstyle/releases/latest \
                       | jq ".tag_name")

mvn -e --no-transfer-progress versions:set -DgroupId=com.puppycrawl.tools -DartifactId=checkstyle \
  -DoldVersion=10.4-SNAPSHOT -DnewVersion="$CS_RELEASE_VERSION"

git add . && git commit -m "config: update version to $CS_RELEASE_VERSION" && push -f origin master

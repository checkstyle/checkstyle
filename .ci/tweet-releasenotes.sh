#!/bin/bash
set -e

source ./.ci/util.sh

echo "PULL_REQUEST:""$PULL_REQUEST"
if [[ $PULL_REQUEST =~ ^([0-9]+)$ ]]; then
  echo "Build is not for Pull Request";
  sleep 5;
  exit 0;
fi

mkdir .ci-temp
if [ -d .ci-temp/contribution ]; then
  cd .ci-temp/contribution/
  git reset --hard origin/master
  git pull origin master
  git fetch --tags
  cd ../../
else
  cd .ci-temp/
  git clone https://github.com/checkstyle/contribution
  cd ../
fi

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
LATEST_RELEASE_TAG=$(git describe $(git rev-list --tags --max-count=1))
cd ../../
CS_RELEASE_VERSION="$(getCheckstylePomVersion)"
echo LATEST_RELEASE_TAG="$LATEST_RELEASE_TAG"
echo CS_RELEASE_VERSION="$CS_RELEASE_VERSION"

export READ_ONLY_TOKEN=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

cd .ci-temp
java -jar contribution/releasenotes-builder/target/releasenotes-builder-1.0-all.jar \
        -localRepoPath checkstyle -remoteRepoPath checkstyle/checkstyle \
        -startRef "$LATEST_RELEASE_TAG" -releaseNumber "$CS_RELEASE_VERSION" \
        -githubAuthToken "$READ_ONLY_TOKEN" \
        -generateTwit -twitterTemplate contribution/releasenotes-builder/src/main/resources/com/\
/github/checkstyle/templates/twitter.template  \
        -publishTwit -twitterProperties C:/Users/ASUS/Documents/twitter.properties

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

checkForVariable "TWITTER_CONSUMER_KEY"
checkForVariable "TWITTER_CONSUMER_SECRET"
checkForVariable "TWITTER_ACCESS_TOKEN"
checkForVariable "TWITTER_ACCESS_TOKEN_SECRET"
checkForVariable "CS_RELEASE_VERSION"
checkForVariable "GITHUB_READ_ONLY_TOKEN"

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
     -remoteRepoPath checkstyle/checkstyle \
     -localRepoPath checkstyle \
     -startRef "$LATEST_RELEASE_TAG" \
     -releaseNumber "$CS_RELEASE_VERSION" \
     -githubAuthToken "$GITHUB_READ_ONLY_TOKEN" \
     -twitterTemplate $BUILDER_RESOURCE_DIR/templates/twitter.template \
     -generateTwit \
     -twitterConsumerKey "$TWITTER_CONSUMER_KEY" \
     -twitterConsumerSecret "$TWITTER_CONSUMER_SECRET" \
     -twitterAccessToken "$TWITTER_ACCESS_TOKEN" \
     -twitterAccessTokenSecret "$TWITTER_ACCESS_TOKEN_SECRET" \
     -publishTwit

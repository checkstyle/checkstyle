#!/bin/bash
set -e

source ./.ci/util.sh

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
| grep -oP '"tag_name": "\K(.*)(?=")')
echo LATEST_RELEASE_TAG="$LATEST_RELEASE_TAG"

cd ../
BUILDER_TEMPLATE_DIR="contribution/releasenotes-builder/src/main/resources/com/github/checkstyle"

checkForVariable() {
    if [[ "$(declare -p "$1")" =~ ^declare\ -x ]]; then
       echo "Error: Define $1 environment variable"
       exit 1
    fi
}

checkForVariable "TWITTER_CONSUMER_KEY"
checkForVariable "TWITTER_CONSUMER_SECRET"
checkForVariable "TWITTER_ACCESS_TOKEN"
checkForVariable "TWITTER_ACCESS_TOKEN_SECRET"

java -jar contribution/releasenotes-builder/target/releasenotes-builder-1.0-all.jar \
     -localRepoPath checkstyle \
     -remoteRepoPath checkstyle/checkstyle \
     -startRef "$LATEST_RELEASE_TAG" \
     -releaseNumber "$CS_RELEASE_VERSION" \
     -githubAuthToken "$READ_ONLY_TOKEN" \
     -generateTwit \
     -twitterTemplate $BUILDER_TEMPLATE_DIR/templates/twitter.template \
     -publishTwit \
     -twitterConsumerKey "$TWITTER_CONSUMER_KEY" \
     -twitterConsumerSecret "$TWITTER_CONSUMER_SECRET" \
     -twitterAccessToken "$TWITTER_ACCESS_TOKEN" \
     -twitterAccessTokenSecret "$TWITTER_ACCESS_TOKEN_SECRET"

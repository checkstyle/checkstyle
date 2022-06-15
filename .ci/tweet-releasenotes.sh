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

checkout_from https://github.com/checkstyle/checkstyle

cd .ci-temp/checkstyle
LATEST_RELEASE_TAG=$(git describe $(git rev-list --tags --max-count=1))
cd ../../
CS_RELEASE_VERSION="$(getCheckstylePomVersion)"
echo LATEST_RELEASE_TAG="$LATEST_RELEASE_TAG"
echo CS_RELEASE_VERSION="$CS_RELEASE_VERSION"

TwitterDir="contribution/releasenotes-builder/src/main/resources/com/github/checkstyle/templates/"

cd .ci-temp
java -jar contribution/releasenotes-builder/target/releasenotes-builder-1.0-all.jar \
        -localRepoPath checkstyle -remoteRepoPath checkstyle/checkstyle \
        -startRef "$LATEST_RELEASE_TAG" -releaseNumber "$CS_RELEASE_VERSION" \
        -githubAuthToken "$READ_ONLY_TOKEN" \
        -generateTwit -twitterTemplate $TwitterDir/twitter.template -publishTwit \
        -twitterConsumerKey "$TWITTER_CONSUMER_KEY" \
        -twitterConsumerSecret "$TWITTER_CONSUMER_SECRET" \
        -twitterAccessToken "$TWITTER_ACCESS_TOKEN" \
        -twitterAccessTokenSecret "$TWITTER_ACCESS_TOKEN_SECRET"

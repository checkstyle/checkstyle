#!/bin/bash
set -e

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

CS_RELEASE_VERSION=$(mvn -e --no-transfer-progress -q -Dexec.executable='echo' \
              -Dexec.args='${project.version}' \
              --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec | sed 's/-SNAPSHOT//')
echo LATEST_RELEASE_TAG="$LATEST_RELEASE_TAG"
echo CS_RELEASE_VERSION="$CS_RELEASE_VERSION"

TWITTER_CONSUMER_KEY=RKrirxHZmStFsP7faO6Cf1QSF
echo TWITTER_CONSUMER_KEY="$TWITTER_CONSUMER_KEY"

TWITTER_CONSUMER_SECRET=DIRkRQhVQYvWgVnPezO05g2KfUdfVLqe3qyTfvYfynRbitexBV
echo TWITTER_CONSUMER_SECRET="$TWITTER_CONSUMER_SECRET"

TWITTER_ACCESS_TOKEN=1280105221989916675-3WN7CjTyYHLPK1IVmODZ6XCxBHcBmL
echo TWITTER_ACCESS_TOKEN="$TWITTER_ACCESS_TOKEN"

TWITTER_ACCESS_TOKEN_SECRET=SI2X8WEgPAzPpxyb7Ek3BNcRtKKqFoXRomnfDvSlSoyTQ
echo TWITTER_ACCESS_TOKEN_SECRET="$TWITTER_ACCESS_TOKEN_SECRET"

cd .ci-temp

java -jar contribution/releasenotes-builder/target/releasenotes-builder-1.0-all.jar \
        -localRepoPath checkstyle -remoteRepoPath checkstyle/checkstyle \
        -startRef "$LATEST_RELEASE_TAG" -releaseNumber "$CS_RELEASE_VERSION" \
        -githubAuthToken "$READ_ONLY_TOKEN" \
        -generateTwit -twitterTemplate contribution/releasenotes-builder/src/main/resources/com/github/checkstyle/templates/twitter.template \
         -publishTwit \
        -twitterConsumerKey "$TWITTER_CONSUMER_KEY" \
        -twitterConsumerSecret "$TWITTER_CONSUMER_SECRET" \
        -twitterAccessToken "$TWITTER_ACCESS_TOKEN" \
        -twitterAccessTokenSecret "$TWITTER_ACCESS_TOKEN_SECRET" \

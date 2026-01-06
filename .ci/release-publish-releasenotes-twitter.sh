#!/bin/bash
set -e

source ./.ci/util.sh

checkForVariable "TWITTER_CONSUMER_KEY"
checkForVariable "TWITTER_CONSUMER_SECRET"
checkForVariable "TWITTER_ACCESS_TOKEN"
checkForVariable "TWITTER_ACCESS_TOKEN_SECRET"
checkForVariable "GITHUB_READ_ONLY_TOKEN"
checkForVariable "GITHUB_REPOSITORY_OWNER"

checkout_from https://github.com/checkstyle/contribution

cd .ci-temp/contribution/releasenotes-builder
./mvnw -e --no-transfer-progress clean compile package
cd ../../../

if [ -d .ci-temp/checkstyle ]; then
  cd .ci-temp/checkstyle/
  git reset --hard origin/main
  git pull origin main
  git fetch --tags
  cd ../../
else
  cd .ci-temp/
  git clone https://github.com/"$GITHUB_REPOSITORY_OWNER"/checkstyle
  cd ../
fi

cd .ci-temp/checkstyle

curl --fail-with-body https://api.github.com/repos/"$GITHUB_REPOSITORY_OWNER"/checkstyle/releases \
 -H "Authorization: token $GITHUB_READ_ONLY_TOKEN" \
 -o /var/tmp/cs-releases.json

TARGET_RELEASE_NUM=$1
TARGET_RELEASE_INDEX=$(cat /var/tmp/cs-releases.json | \
    jq "[.[].tag_name] | to_entries | .[] | \
    select(.value==\"checkstyle-$TARGET_RELEASE_NUM\") | .key")
echo TARGET_RELEASE_INDEX="$TARGET_RELEASE_INDEX"

PREVIOUS_RELEASE_INDEX=$(($TARGET_RELEASE_INDEX+1))
echo PREVIOUS_RELEASE_INDEX="$PREVIOUS_RELEASE_INDEX"

END_REF=$(cat /var/tmp/cs-releases.json | jq -r ".[$TARGET_RELEASE_INDEX].tag_name")
START_REF=$(cat /var/tmp/cs-releases.json | jq -r ".[$PREVIOUS_RELEASE_INDEX].tag_name")

echo START_REF="$START_REF"
echo END_REF="$END_REF"

cd ../
BUILDER_RESOURCE_DIR="contribution/releasenotes-builder/src/main/resources/com/github/checkstyle"

java -jar contribution/releasenotes-builder/target/releasenotes-builder-1.0-all.jar \
     -remoteRepoPath "$GITHUB_REPOSITORY_OWNER"/checkstyle \
     -localRepoPath checkstyle \
     -startRef "$START_REF" \
     -endRef "$END_REF" \
     -releaseNumber "$TARGET_RELEASE_NUM" \
     -githubAuthToken "$GITHUB_READ_ONLY_TOKEN" \
     -twitterTemplate $BUILDER_RESOURCE_DIR/templates/twitter.template \
     -generateTwit \
     -twitterConsumerKey "$TWITTER_CONSUMER_KEY" \
     -twitterConsumerSecret "$TWITTER_CONSUMER_SECRET" \
     -twitterAccessToken "$TWITTER_ACCESS_TOKEN" \
     -twitterAccessTokenSecret "$TWITTER_ACCESS_TOKEN_SECRET" \
     -publishTwit

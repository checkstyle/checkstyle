#!/bin/bash
set -e

source ./.ci/util.sh

checkForVariable "GITHUB_TOKEN"

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

curl https://api.github.com/repos/checkstyle/checkstyle/releases -o /var/tmp/cs-releases.json
TARGET_RELEASE_INDEX=$(cat /var/tmp/cs-releases.json | \
    jq '[.[].tag_name] | to_entries | .[] | select(.value=="checkstyle-$1") | .key')
echo "$TARGET_RELEASE_INDEX"
PREVIOUS_RELEASE_INDEX=$(($TARGET_RELEASE_INDEX+1))
echo "$PREVIOUS_RELEASE_INDEX"

START_REF=$(cat /var/tmp/cs-releases.json | jq -r ".[$TARGET_RELEASE_INDEX].tag_name")
END_REF=$(cat /var/tmp/cs-releases.json | jq -r ".[$PREVIOUS_RELEASE_INDEX].tag_name")

echo START_REF="$START_REF"
echo END_REF="$END_REF"

cd ../

BUILDER_RESOURCE_DIR="contribution/releasenotes-builder/src/main/resources/com/github/checkstyle"

java -jar contribution/releasenotes-builder/target/releasenotes-builder-1.0-all.jar \
     -localRepoPath checkstyle \
     -remoteRepoPath checkstyle/checkstyle \
     -startRef "$START_REF" \
     -endRef "$END_REF" \
     -releaseNumber "$CS_RELEASE_VERSION" \
     -githubAuthToken "$READ_ONLY_TOKEN" \
     -generateGitHub \
     -gitHubTemplate $BUILDER_RESOURCE_DIR/templates/github_post.template

echo ==============================================
echo "GITHUB PAGE:"
echo ==============================================
CONTENT=$(cat github_post.txt)
echo "$CONTENT"

NEW_RELEASE=$(git describe --abbrev=0 | cut -d '-' -f 2)
echo NEW_RELEASE="$NEW_RELEASE"

echo "Updating Github tag page"
curl -i -X POST https://api.github.com/repos/checkstyle/checkstyle/releases \
  -H "Authorization: token $GITHUB_TOKEN" \
  -d "{ \"tag_name\": \"checkstyle-$NEW_RELEASE\", \
        \"body\": \"$CONTENT\" }"

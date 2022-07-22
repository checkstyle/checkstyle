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

cd .ci-temp

NEW_RELEASE=$(git describe --abbrev=0 | cut -d '-' -f 2)
PREV_RELEASE=$(git describe --abbrev=0 --tags \
        $(git rev-list --tags --skip=1 --max-count=1) \
    | cut -d '-' -f 2)
FUTURE_RELEASE=$(xmlstarlet sel -N pom=http://maven.apache.org/POM/4.0.0 \
           -t -m pom:project -v pom:version pom.xml | sed "s/-SNAPSHOT//")
TKN=$(cat ~/.m2/token-checkstyle.txt)

BUILDER_RESOURCE_DIR="contribution/releasenotes-builder/src/main/resources/com/github/checkstyle"

java -jar contribution/releasenotes-builder/target/releasenotes-builder-1.0-all.jar \
     -localRepoPath checkstyle \
     -remoteRepoPath checkstyle/checkstyle \
     -startRef "$LATEST_RELEASE_TAG" \
     -releaseNumber "$CS_RELEASE_VERSION" \
     -githubAuthToken "$READ_ONLY_TOKEN" \
     -generateXdoc \
     -xdocTemplate $BUILDER_RESOURCE_DIR/templates/GitHub_post.template

echo "Updating Github tag page"
curl -i -H "Authorization: token $TKN" \
  -d "{ \"tag_name\": \"checkstyle-$NEW_RELEASE\", \
        \"target_commitish\": \"master\", \
        \"name\": \"\", \
        \"body\": \"https://checkstyle.org/releasenotes.html#Release_$NEW_RELEASE\", \
        \"draft\": false,   \"prerelease\": false }" \
  -X POST https://api.github.com/repos/checkstyle/checkstyle/releases

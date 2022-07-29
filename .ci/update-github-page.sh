#!/bin/bash
set -e

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
     -generateGitHub \
     -gitHubTemplate $BUILDER_RESOURCE_DIR/templates/github_post.template

echo ==============================================
echo
echo "xdoc segment:"
echo ==============================================
POST=$(cat github_post.txt)
echo "$POST"

NEW_RELEASE=$(git describe --abbrev=0 | cut -d '-' -f 2)
echo NEW_RELEASE="$NEW_RELEASE"

echo "Updating Github tag page"
curl -i -H "Authorization: token $READ_ONLY_TOKEN" \
  -d "{ \"tag_name\": \"checkstyle-$NEW_RELEASE\", \
        \"target_commitish\": \"master\", \
        \"name\": \"\", \
        \"body\": \"https://checkstyle.org/releasenotes.html#Release_$NEW_RELEASE\n
                   $POST\", \
        \"draft\": false,   \"prerelease\": false }" \
  -X POST https://api.github.com/repos/checkstyle/checkstyle/releases
  
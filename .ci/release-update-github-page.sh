#!/bin/bash
set -e

source ./.ci/util.sh

if [[ -z $1 ]]; then
  echo "release number is not set"
  echo "usage: $BASH_SOURCE {release number}"
  exit 1
fi
TARGET_VERSION=$1
echo TARGET_VERSION="$TARGET_VERSION"

checkForVariable "GITHUB_TOKEN"
checkForVariable "GITHUB_REPOSITORY_OWNER"

checkout_from https://github.com/checkstyle/contribution

cd .ci-temp/contribution/releasenotes-builder
mvn -e --no-transfer-progress clean compile package
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
 -H "Authorization: token $GITHUB_TOKEN" \
 -o /var/tmp/cs-releases.json

# Last release is at index 0.
START_REF=$(cat /var/tmp/cs-releases.json | jq -r ".[0].tag_name")
END_REF="checkstyle-$TARGET_VERSION"

echo START_REF="$START_REF"
echo END_REF="$END_REF"

cd ../

BUILDER_RESOURCE_DIR="contribution/releasenotes-builder/src/main/resources/com/github/checkstyle"

java -jar contribution/releasenotes-builder/target/releasenotes-builder-1.0-all.jar \
     -localRepoPath checkstyle \
     -remoteRepoPath "$GITHUB_REPOSITORY_OWNER"/checkstyle \
     -startRef "$START_REF" \
     -endRef "$END_REF" \
     -releaseNumber "$TARGET_VERSION" \
     -githubAuthToken "$GITHUB_TOKEN" \
     -generateGitHub \
     -gitHubTemplate $BUILDER_RESOURCE_DIR/templates/github_post.template

echo ==============================================
echo "GITHUB PAGE:"
echo ==============================================
CONTENT=$(cat github_post.txt)
echo "$CONTENT"

echo 'Updating content to be be json value friendly'
UPDATED_CONTENT=$(awk '{printf "%s\n", $0}' github_post.txt | jq -Rsa .)
echo "$UPDATED_CONTENT"

cat <<EOF > body.json
{
  "tag_name": "checkstyle-$TARGET_VERSION",
  "body": ${UPDATED_CONTENT},
  "target_commitish": "main",
  "name": "",
  "draft": false,
  "prerelease": false
}
EOF
echo "JSON Body"
cat body.json

echo "Creating Github release"
curl --fail-with-body \
  -X POST https://api.github.com/repos/"$GITHUB_REPOSITORY_OWNER"/checkstyle/releases \
  -H "Accept: application/vnd.github+json" \
  -H "Authorization: token $GITHUB_TOKEN" \
  --data @body.json

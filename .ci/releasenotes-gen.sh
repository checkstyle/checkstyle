#!/bin/bash
# Attention: script uses 'set -e' for fail-fast behavior
# to run on local:
# export READ_ONLY_TOKEN=9ffd28f
#  && export DRONE_PULL_REQUEST="master" && ./.ci/releasenotes-gen.sh

set -e

if [ -z "$READ_ONLY_TOKEN" ]; then
  echo "'READ_ONLY_TOKEN' not found, exiting..."
  sleep 5s;
  exit 1;
fi

echo "PULL_REQUEST:""$PULL_REQUEST"
if [[ $PULL_REQUEST =~ ^([0-9]+)$ ]]; then
  echo "Build is not for Pull Request";
  sleep 5s;
  exit 0;
fi

mkdir -p .ci-temp
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

# Perform full clone to ensure all tags and commit history are available
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
LATEST_RELEASE_TAG=$(git describe "$(git rev-list --tags --max-count=1)")
cd ../../

# shellcheck disable=2016 # we do not want to expand properties in this command
CS_RELEASE_VERSION=$(mvn -e --no-transfer-progress -q -Dexec.executable='echo' \
              -Dexec.args='${project.version}' \
              --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec | sed 's/-SNAPSHOT//')
echo LATEST_RELEASE_TAG="$LATEST_RELEASE_TAG"
echo CS_RELEASE_VERSION="$CS_RELEASE_VERSION"
cd .ci-temp
java -jar contribution/releasenotes-builder/target/releasenotes-builder-1.0-all.jar \
        -localRepoPath checkstyle -remoteRepoPath checkstyle/checkstyle \
        -startRef "$LATEST_RELEASE_TAG" -releaseNumber "$CS_RELEASE_VERSION" \
        -githubAuthToken "$READ_ONLY_TOKEN" -generateAll -validateVersion

echo ==============================================
echo
echo "xdoc segment:"
echo ==============================================
cat xdoc.xml
echo ==============================================
echo
echo "twitter post:"
echo ==============================================
cat twitter.txt
echo ==============================================
echo
echo "GitHub post:"
echo ==============================================
cat github_post.txt
echo ==============================================

find . -delete

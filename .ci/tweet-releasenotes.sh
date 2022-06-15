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

cd .ci-temp
java -jar contribution/releasenotes-builder/target/releasenotes-builder-1.0-all.jar \
        -localRepoPath checkstyle -remoteRepoPath checkstyle/checkstyle \
        -startRef "$LATEST_RELEASE_TAG" -releaseNumber "$CS_RELEASE_VERSION" \
        -githubAuthToken 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 \
        -generateTwit -twitterTemplate contribution/releasenotes-builder/src/main/resources/com/github/checkstyle/templates/twitter.template  \
        -publishTwit -twitterConsumerKey kILd2UbNzg8Rn1crj4AaO16D3 \
        -twitterConsumerSecret Ikx10jwE9Ks0zI47Lhsj8GpnI5cbiXCLfFwIeIa7nzfTH5WOOj \
        -twitterAccessToken 1280105221989916675-zXkemcC6t6nCi4OehS6O85ys8qtIel \
        -twitterAccessTokenSecret IGu78pA7WTQGb1H1fK2QqXiFKdWDGXeZGUVYrLvcMucug \

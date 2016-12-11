#!/bin/bash
# Attention, there is no "-x" to avoid problem on Travis
set -e

if [[ $TRAVIS_PULL_REQUEST =~ ^([0-9]*)$ ]]; then exit 0; fi
git clone https://github.com/checkstyle/contribution
cd contribution/releasenotes-builder
mvn clean compile package
cd ../../
git clone https://github.com/checkstyle/checkstyle
cd checkstyle
LATEST_RELEASE_TAG=$(git describe $(git rev-list --tags --max-count=1))
cd ../
CS_RELEASE_VERSION=$(mvn -q -Dexec.executable='echo' -Dexec.args='${project.version}' --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec | sed 's/-SNAPSHOT//' ) 
echo LATEST_RELEASE_TAG=$LATEST_RELEASE_TAG
echo CS_RELEASE_VERSION=$CS_RELEASE_VERSION
java -jar contribution/releasenotes-builder/target/releasenotes-builder-1.0-all.jar \
        -localRepoPath checkstyle -startRef $LATEST_RELEASE_TAG -releaseNumber $CS_RELEASE_VERSION \
        -githubAuthToken $GITHUB_AUTH_TOKEN -generateAll -publishXdoc

echo ==============================================
echo xdoc
echo
cat xdoc.xml
echo twitter post
echo
cat twitter.txt
echo google plus post
echo
cat gplus.txt
echo RSS post
echo
cat rss.txt
echo mailing list post
echo
cat mailing_list.txt
cd checkstyle/src/xdocs
echo releasenotes.xml after commit
echo
head -n 100 releasenotes.xml

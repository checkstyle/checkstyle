#!/usr/bin/env bash
set -e

echo "Make sure you prepared your PC for automative deployment"
echo "Release process: https://github.com/checkstyle/checkstyle/wiki/How-to-make-a-release"

RELEASE=$(xmlstarlet sel -N pom=http://maven.apache.org/POM/4.0.0 \
           -t -m pom:project -v pom:version pom.xml | sed "s/-SNAPSHOT//")
PREV_RELEASE=$(git describe --abbrev=0 $(git rev-list --tags --max-count=1) | sed "s/checkstyle-//")

echo "PREVIOUS RELEASE version:"$PREV_RELEASE
echo "RELEASE version:"$RELEASE

if [[ ! -f ~/.m2/token-checkstyle.txt ]]; then
  echo "File ~/.m2/token-checkstyle.txt with Github token is not found"
  exit 1
fi
if [[ -z $RELEASE ]]; then
  echo "Problem to calculate release version."
  exit 1
fi
if [[ -z $PREV_RELEASE ]]; then
  echo "Problem to calculate previous release version."
  exit 1
fi
if [[ $(grep "<section name=\"Release $RELEASE\">" src/xdocs/releasenotes.xml \
           | cat | wc -l) -eq 0 ]]; then
  echo "src/xdocs/releasenotes.xml do not have section for $RELEASE"
  exit 1
fi

SKIP_TEST="-DskipTests -DskipITs"
SKIP_CHECKSTYLE="-Dcheckstyle.ant.skip=true -Dcheckstyle.skip=true"
SKIP_OTHERS="-Dpmd.skip=true -Dspotbugs.skip=true -Djacoco.skip=true -Dxml.skip=true"

echo "Version bump in pom.xml (release:prepare) ..."
mvn -e --no-transfer-progress -Pgpg release:prepare -B -Darguments="$SKIP_TEST $SKIP_CHECKSTYLE \
  $SKIP_OTHERS"

echo "Deployment of jars to maven central (release:perform) ..."
mvn -e --no-transfer-progress -Pgpg release:perform -Darguments="$SKIP_CHECKSTYLE"

echo "Go to folder where site was build and sources are already at required tag"
cd target/checkout

echo "Generating web site"
mvn -e --no-transfer-progress site

echo "Generating uber jar ...(no clean to keep site resources just in case)"
mvn -e --no-transfer-progress -Passembly package

echo "Come back repo folder"
cd ../../

##############################

NEW_RELEASE=$(git describe --abbrev=0 | cut -d '-' -f 2)
PREV_RELEASE=$(git describe --abbrev=0 --tags \
        `git rev-list --tags --skip=1 --max-count=1` \
    | cut -d '-' -f 2)
FUTURE_RELEASE=$(xmlstarlet sel -N pom=http://maven.apache.org/POM/4.0.0 \
           -t -m pom:project -v pom:version pom.xml | sed "s/-SNAPSHOT//")
TKN=$(cat ~/.m2/token-checkstyle.txt)

echo "Updating Github tag page"
curl -i -H "Authorization: token $TKN" \
  -d "{ \"tag_name\": \"checkstyle-$NEW_RELEASE\", \
        \"target_commitish\": \"master\", \
        \"name\": \"\", \
        \"body\": \"https://checkstyle.org/releasenotes.html#Release_$NEW_RELEASE\", \
        \"draft\": false,   \"prerelease\": false }" \
  -X POST https://api.github.com/repos/checkstyle/checkstyle/releases

echo "Publishing 'all' jar to Github"
RELEASE_ID=$(curl -s -X GET \
  https://api.github.com/repos/checkstyle/checkstyle/releases/tags/checkstyle-$NEW_RELEASE \
  | jq ".id")
curl -i -H "Authorization: token $TKN" \
  -H "Content-Type: application/zip" \
  --data-binary @"target/checkout/target/checkstyle-$NEW_RELEASE-all.jar" \
  -X POST https://uploads.github.com/repos/checkstyle/checkstyle/releases/$RELEASE_ID/assets?name=checkstyle-$NEW_RELEASE-all.jar

echo "Close previous milestone at github"
MILESTONE_ID=$(curl -s \
                -X GET https://api.github.com/repos/checkstyle/checkstyle/milestones?state=open \
                | jq ".[0] | .number")
curl -i -H "Authorization: token $TKN" \
  -d "{ \"state\": \"closed\" }" \
  -X PATCH https://api.github.com/repos/checkstyle/checkstyle/milestones/$MILESTONE_ID


echo "Creation of new milestone ..."
LAST_SUNDAY_DAY=$(cal -d $(date -d "next month" +"%Y-%m") \
                    | awk '/^ *[0-9]/ { d=$1 } END { print d }')
LAST_SUNDAY_DATETIME=$(date -d "next month" +"%Y-%m")"-$LAST_SUNDAY_DAY""T08:00:00Z"
echo $LAST_SUNDAY_DATETIME
curl -i -H "Authorization: token $TKN" \
  -d "{ \"title\": \"$FUTURE_RELEASE\", \
        \"state\": \"open\", \
        \"description\": \"\", \
        \"due_on\": \"$LAST_SUNDAY_DATETIME\" \
        }" \
  -X POST https://api.github.com/repos/checkstyle/checkstyle/milestones

echo "Creation of issue in eclipse-cs repo ..."
curl -i -H "Authorization: token $TKN" \
  -d "{ \"title\": \"upgrade to checkstyle $NEW_RELEASE\", \
        \"body\": \"https://checkstyle.org/releasenotes.html#Release_$NEW_RELEASE\" \
        }" \
  -X POST https://api.github.com/repos/checkstyle/eclipse-cs/issues

echo "Creation of issue in sonar-checkstyle repo ..."
curl -i -H "Authorization: token $TKN" \
  -d "{ \"title\": \"upgrade to checkstyle $NEW_RELEASE\", \
        \"body\": \"https://checkstyle.org/releasenotes.html#Release_$NEW_RELEASE\" \
        }" \
  -X POST https://api.github.com/repos/checkstyle/sonar-checkstyle/issues

##############################

echo "Switch to checkstyle.github.io repository"
if [ -d "../checkstyle.github.io" ] ; then
  cd ../checkstyle.github.io
else
  cd ../
  echo "Clone by ssh only to avoid passwords on push ..."
  git clone git@github.com:checkstyle/checkstyle.github.io.git
  cd checkstyle.github.io
fi
git rm -rf *
git checkout HEAD -- CNAME
cp -R ../checkstyle/target/checkout/target/site/* .
git add .
git commit -m "release $RELEASE"
echo "Push site content to remote ..."
echo "We do force to avoid history changes, we do not need history as github.io shows only HEAD."
git push origin --force

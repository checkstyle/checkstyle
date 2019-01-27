#!/usr/bin/env bash
set -e

echo "Make sure you prepared your PC for automative deployment"
echo "Release process: https://github.com/checkstyle/checkstyle/wiki/How-to-make-a-release"

RELEASE=$(xmlstarlet sel -N pom=http://maven.apache.org/POM/4.0.0 \
           -t -m pom:project -v pom:version pom.xml | sed "s/-SNAPSHOT//")
PREV_RELEASE=$(git describe --abbrev=0 $(git rev-list --tags --max-count=1) | sed "s/checkstyle-//")

echo "PREVIOUS RELEASE version:"$PREV_RELEASE
echo "RELEASE version:"$RELEASE

if [[ -z $RELEASE ]]; then
  echo "Problem to calculate release version."
  exit 1
fi
if [[ -z $PREV_RELEASE ]]; then
  echo "Problem to calculate previous release version."
  exit 1
fi

echo "Version bump in pom.xml (release:prepare) ..."
SKIP_TEST="-DskipTests -DskipITs"
SKIP_CHECKSTYLE="-Dcheckstyle.ant.skip=true -Dcheckstyle.skip=true"
SKIP_OTHERS="-Dpmd.skip=true -Dspotbugs.skip=true -Djacoco.skip=true -Dxml.skip=true"
mvn -e -Pgpg release:prepare -B -Darguments="$SKIP_TEST $SKIP_CHECKSTYLE $SKIP_OTHERS"

echo "Deployment of jars to maven central (release:perform) ..."
mvn -e -Pgpg release:perform -Darguments="$SKIP_CHECKSTYLE"

echo "Go to folder where site was build and sources are already at required tag"
cd target/checkout

echo "Generating web site"
mvn -e site

echo "Generating uber jar ...(no clean to keep site resources just in case)"
mvn -e -Passembly package

echo "Come back repo folder"
cd ../../

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

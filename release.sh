#!/usr/bin/env bash
set -e

# Make sure you prepared your PC for automative deployment
# https://github.com/checkstyle/checkstyle/wiki/How-to-make-a-release

SF_USER=romanivanov
RELEASE=$(xmlstarlet sel -N pom=http://maven.apache.org/POM/4.0.0 \
           -t -m pom:project -v pom:version pom.xml | sed "s/-SNAPSHOT//")
PREV_RELEASE=$(git describe $(git rev-list --tags --max-count=1) | sed "s/checkstyle-//")

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

#############################
echo "Please provide password for $SF_USER,checkstyle@shell.sourceforge.net"
echo "exit" | ssh -t $SF_USER,checkstyle@shell.sourceforge.net create

# Version bump in pom.xml - https://github.com/checkstyle/checkstyle/commits/master
SKIP_TEST="-DskipTests -DskipITs"
SKIP_CHECKSTYLE="-Dcheckstyle.ant.skip=true -Dcheckstyle.skip=true"
SKIP_OTHERS="-Dpmd.skip=true -Dspotbugs.skip=true -Djacoco.skip=true -Dxml.skip=true"
mvn -e -Pgpg release:prepare -B -Darguments="$SKIP_TEST $SKIP_CHECKSTYLE $SKIP_OTHERS"

# deployment of jars to maven central
# and publication of site to http://checkstyle.sourceforge.net/new-site/
mvn -e -Pgpg release:perform -Darguments="$SKIP_CHECKSTYLE"

#############################

ssh $SF_USER,checkstyle@shell.sourceforge.net << EOF


#Swap html content
cd /home/project-web/checkstyle
mv htdocs/new-site/ .
mv htdocs htdocs-$PREV_RELEASE
mv new-site htdocs
ln -s /home/project-web/checkstyle/reports htdocs/reports

#Archiving
tar cfz htdocs-$PREV_RELEASE.tar.gz htdocs-$PREV_RELEASE/
rm -rf htdocs-$PREV_RELEASE/

EOF

##############################

# go to folder where site was build and sources are already at required tag
cd target/checkout

#Generate all binaries, no clean to keep site resources just in case
mvn -e -Passembly package

#Publish them to sourceforce
FRS_PATH=/home/frs/project/checkstyle/checkstyle/$RELEASE
ssh $SF_USER,checkstyle@shell.sourceforge.net "mkdir -p $FRS_PATH"
# !!! THIS WILL ASK A SOURCEFORGE PASSWORD !!
scp target/*.zip target/*.tar.gz target/*.jar $SF_USER@frs.sourceforge.net:$FRS_PATH

#come back repo folder
cd ../../

##############################

#update github.io
if [ -d "../checkstyle.github.io" ] ; then
  cd ../checkstyle.github.io
else
  cd ../
  # clone by ssh only to avoid passwords on push
  git clone git@github.com:checkstyle/checkstyle.github.io.git
  cd checkstyle.github.io
fi
git rm -rf *
git checkout HEAD -- CNAME
cp -R ../checkstyle/target/checkout/target/site/* .
git add .
git commit -m "release $RELEASE"
# we do force to avoid history changes, we do not need history as github.io shows only HEAD.
git push origin --force

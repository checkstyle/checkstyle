#!/usr/bin/env bash
set -e

# Make sure you prepared your PC for automative deployment
# https://github.com/checkstyle/checkstyle/wiki/How-to-make-a-release

SF_USER=romanivanov
RELEASE=$(xmlstarlet sel -N pom=http://maven.apache.org/POM/4.0.0 -t -m pom:project -v pom:version pom.xml | sed "s/-SNAPSHOT//")
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
mvn -Pgpg release:prepare -B -Darguments="-DskipTests -DskipITs -Dpmd.skip=true -Dfindbugs.skip=true -Dcobertura.skip=true -Dcheckstyle.ant.skip=true -Dcheckstyle.skip=true -Dxml.skip=true"

# deployment of jars to maven central and publication of site to http://checkstyle.sourceforge.net/new-site/
mvn -Pgpg release:perform -Darguments='-Dcheckstyle.ant.skip=true'

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

git checkout checkstyle-$RELEASE

#Generate all binaries
mvn -Passembly clean package

#Publish them to sourceforce
FRS_PATH=/home/frs/project/checkstyle/checkstyle/$RELEASE
ssh $SF_USER,checkstyle@shell.sourceforge.net "mkdir -p $FRS_PATH"
# !!! THIS WILL ASK A SOURCEFORGE PASSWORD !!
scp target/*.jar $SF_USER@frs.sourceforge.net:$FRS_PATH
scp target/*.tar.gz $SF_USER@frs.sourceforge.net:$FRS_PATH
scp target/*.zip $SF_USER@frs.sourceforge.net:$FRS_PATH

git checkout master

##############################

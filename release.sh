#!/usr/bin/bash
set -e

#https://github.com/checkstyle/checkstyle/wiki/How-to-make-a-release

SF_USER=romanivanov
PREV_RELEASE=6.16.1
RELEASE=$(git describe $(git rev-list --tags --max-count=1) | sed "s/checkstyle-//")

echo "PREVIOUS RELESE version:"$PREV_RELEASE
echo "RELESE version:"$RELEASE

#############################

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
# !!! THIS WILL AS A PASSWORD !!
scp target/*.jar $SF_USER@frs.sourceforge.net:$FRS_PATH
scp target/*.tar.gz $SF_USER@frs.sourceforge.net:$FRS_PATH
scp target/*.zip $SF_USER@frs.sourceforge.net:$FRS_PATH

git checkout master

##############################

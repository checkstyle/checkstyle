#!/usr/bin/env bash
set -e

echo "Make sure you prepared your PC for automative deployment"
echo "Release process: https://github.com/checkstyle/checkstyle/wiki/How-to-make-a-release"

SF_USER=romanivanov
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

#############################
echo "Please provide password for $SF_USER,checkstyle@shell.sourceforge.net"
echo "exit" | ssh -t $SF_USER,checkstyle@shell.sourceforge.net create

echo "Version bump in pom.xml"
SKIP_TEST="-DskipTests -DskipITs"
SKIP_CHECKSTYLE="-Dcheckstyle.ant.skip=true -Dcheckstyle.skip=true"
SKIP_OTHERS="-Dpmd.skip=true -Dspotbugs.skip=true -Djacoco.skip=true -Dxml.skip=true"
mvn -e -Pgpg release:prepare -B -Darguments="$SKIP_TEST $SKIP_CHECKSTYLE $SKIP_OTHERS"

echo "Deployment of jars to maven central"
echo "and publication of site to http://checkstyle.sourceforge.net/new-site/"
mvn -e -Pgpg release:perform -Darguments="$SKIP_CHECKSTYLE"

#############################

ssh $SF_USER,checkstyle@shell.sourceforge.net << EOF

echo "Swap html content"
cd /home/project-web/checkstyle
mv htdocs/new-site/ .
mv htdocs htdocs-$PREV_RELEASE
mv new-site htdocs
ln -s /home/project-web/checkstyle/reports htdocs/reports
echo "restore folder with links to old releases"
mv htdocs-$PREV_RELEASE/version htdocs

echo "Archiving"
tar cfz htdocs-$PREV_RELEASE.tar.gz htdocs-$PREV_RELEASE/
mv htdocs-$PREV_RELEASE.tar.gz htdocs-archive/
rm -rf htdocs-$PREV_RELEASE/

echo "Extracting archive to previous releases documentation"
tar -xzvf htdocs-archive/htdocs-$PREV_RELEASE.tar.gz -C htdocs-version/ \
--exclude="*/apidocs" \
--exclude="*/xref" --exclude="*/xref-test" --exclude="*/cobertura" --exclude="*/dsm" \
--exclude="*/api" --exclude="reports" --exclude="jacoco" --exclude="dtds" \
--exclude="dependency-updates-report.html" --exclude="plugin-updates-report.html" \
--exclude="jdepend-report.html" --exclude="failsafe-report.html" \
--exclude="surefire-report.html" \
--exclude="linkcheck.html" --exclude="findbugs.html" --exclude="taglist.html" \
--exclude="releasenotes_old.html" --exclude="dependencies.html"
echo "Make a link to make it accessible from web"
ln -f -s htdocs-version/htdocs-$PREV_RELEASE htdocs/version/$PREV_RELEASE

EOF

##############################

echo "Go to folder where site was build and sources are already at required tag"
cd target/checkout

echo "Generate all binaries, no clean to keep site resources just in case"
mvn -e -Passembly package

echo "Come back repo folder"
cd ../../

##############################

echo "update github.io"
if [ -d "../checkstyle.github.io" ] ; then
  cd ../checkstyle.github.io
else
  cd ../
  echo "Clone by ssh only to avoid passwords on push"
  git clone git@github.com:checkstyle/checkstyle.github.io.git
  cd checkstyle.github.io
fi
git rm -rf *
git checkout HEAD -- CNAME
cp -R ../checkstyle/target/checkout/target/site/* .
git add .
git commit -m "release $RELEASE"
echo "We do force to avoid history changes, we do not need history as github.io shows only HEAD."
git push origin --force

#!/usr/bin/env bash

set -e

PREV_RELEASE=$1
RELEASE=$2

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

SF_USER=romanivanov
#############################
echo "Please provide password for $SF_USER,checkstyle@shell.sourceforge.net"
echo "exit" | ssh -t $SF_USER,checkstyle@shell.sourceforge.net create

#####

mkdir -p .ci-temp
cd .ci-temp
rm -fr checkstyle.github.io
echo "Clone by ssh only to avoid passwords on push"
git clone git@github.com:checkstyle/checkstyle.github.io.git
echo "clean up git files"
rm -rf checkstyle.github.io/.git
rm -rf checkstyle.github.io/CNAME
echo "Archiving ..."
tar cfz checkstyle.github.io.tar.gz checkstyle.github.io
echo "Uploading to sourceforge ..."
scp checkstyle.github.io.tar.gz \
  $SF_USER,checkstyle@shell.sourceforge.net:/home/project-web/checkstyle/

#############################

ssh $SF_USER,checkstyle@shell.sourceforge.net << EOF

echo "Swap html content"
cd /home/project-web/checkstyle
tar -xzvf checkstyle.github.io.tar.gz
mv htdocs htdocs-$PREV_RELEASE
mv checkstyle.github.io htdocs

echo "create .htaccess for dtds redirection"
cat <<HTACCESS >> htdocs/.htaccess
Redirect 301 "/dtds" "https://checkstyle.org/dtds"
RedirectMatch 301 "/version/.*/dtds/(.*)" "https://checkstyle.org/dtds/\$1"
HTACCESS
chmod o+r htdocs/.htaccess

ln -s /home/project-web/checkstyle/reports htdocs/reports
echo "remove dtds folder from unsecure web site"
rm -r htdocs/dtds
echo "restore folder with links to old releases"
mv htdocs-$PREV_RELEASE/version htdocs

echo "Archiving"
tar cfz htdocs-$PREV_RELEASE.tar.gz htdocs-$PREV_RELEASE/
mv htdocs-$PREV_RELEASE.tar.gz htdocs-archive/
rm -rf htdocs-$PREV_RELEASE/

echo "Extracting archive to previous releases documentation"
tar -xzvf htdocs-archive/htdocs-$PREV_RELEASE.tar.gz -C htdocs-version/ --same-owner \
--exclude="*/apidocs" \
--exclude="*/xref" --exclude="*/xref-test" --exclude="*/cobertura" --exclude="*/dsm" \
--exclude="*/api" --exclude="reports" --exclude="jacoco" --exclude="dtds" \
--exclude="dependency-updates-report.html" --exclude="plugin-updates-report.html" \
--exclude="jdepend-report.html" --exclude="failsafe-report.html" \
--exclude="surefire-report.html" \
--exclude="linkcheck.html" --exclude="findbugs.html" --exclude="taglist.html" \
--exclude="releasenotes_old_6-0_7-8.html" --exclude="releasenotes_old_1-0_5-9.html" \
--exclude="dependencies.html"

echo "Make a link to make it accessible from web"
ln -f -s \$(pwd)/htdocs-version/htdocs-$PREV_RELEASE \$(pwd)/htdocs/version/$PREV_RELEASE

EOF

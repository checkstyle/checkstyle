#!/usr/bin/env bash

set -e

source ./.ci/util.sh

RELEASE=$1

echo "RELEASE version:""$RELEASE"

if [[ -z $RELEASE ]]; then
  echo "Problem to calculate release version."
  exit 1
fi

checkForVariable "SF_USER"

echo "Creating shell for $SF_USER@shell.sourceforge.net"
ssh -i ~/.ssh/private_sourceforge_key "$SF_USER"@shell.sourceforge.net create

echo "Creating .ci-temp if it does not exist"
mkdir -p .ci-temp
cd .ci-temp
rm -fr checkstyle.github.io

echo "Cloning checkstyle.github.io repo"
git clone https://github.com/checkstyle/checkstyle.github.io.git

echo "Cleaning up git files"
rm -rf checkstyle.github.io/.git
rm -rf checkstyle.github.io/CNAME

echo "Archiving"
tar cfz checkstyle.github.io.tar.gz checkstyle.github.io

echo "Uploading to sourceforge"
scp -i ~/.ssh/private_sourceforge_key checkstyle.github.io.tar.gz \
  "$SF_USER"@web.sourceforge.net:/home/project-web/checkstyle

echo "Using shell for $SF_USER@shell.sourceforge.net"
ssh -i ~/.ssh/private_sourceforge_key "$SF_USER"@shell.sourceforge.net << 'EOF'

cd /home/project-web/checkstyle

echo "Extracting previous release version"
PREVIOUS_RELEASE_VERSION_SPAN=$(grep "projectVersion" htdocs/index.html)
REGEX="|<\/span>Version: (.*)<\/li>"
[[ $PREVIOUS_RELEASE_VERSION_SPAN =~ $REGEX ]]
PREV_RELEASE="${BASH_REMATCH[1]}"
echo "PREVIOUS RELEASE version:""$PREV_RELEASE"
if [[ -z "$PREV_RELEASE" ]]
then
  echo "Problem to calculate previous release version."
  exit 1
fi

echo "Swapping html content"
tar -xzvf checkstyle.github.io.tar.gz
mv htdocs htdocs-$PREV_RELEASE
mv checkstyle.github.io htdocs

echo "Creating .htaccess for dtds redirection"
cat <<HTACCESS >> htdocs/.htaccess
Redirect 301 "/dtds" "https://checkstyle.org/dtds"
RedirectMatch 301 "/version/.*/dtds/(.*)" "https://checkstyle.org/dtds/\$1"
HTACCESS
chmod o+r htdocs/.htaccess

ln -s /home/project-web/checkstyle/reports htdocs/reports
echo "Removing dtds folder from unsecure web site"
rm -r htdocs/dtds

echo "Restoring folder with links to old releases"
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

echo "Making a link to make it accessible from web"
ln -f -s $(pwd)/htdocs-version/htdocs-"$PREV_RELEASE" $(pwd)/htdocs/version/"$PREV_RELEASE"

EOF

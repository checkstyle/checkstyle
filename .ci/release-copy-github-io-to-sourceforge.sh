#!/usr/bin/env bash

set -e

source ./.ci/util.sh

checkForVariable "SF_USER"

if [[ -z $1 ]]; then
  echo "Release version is not set"
  echo "usage: $BASH_SOURCE {release version}"
  exit 1
fi

RELEASE_VERSION=$1
echo "RELEASE_VERSION=$RELEASE_VERSION"

REMOTE_PATH="/home/project-web/checkstyle"
SSH_KEY_PATH="$HOME/.ssh/private_sourceforge_key"

echo "Preparing local deployment workspace in .ci-temp"
mkdir -p .ci-temp
cd .ci-temp
rm -fr checkstyle.github.io
rm -fr sourceforge

echo "Cloning the current checkstyle.github.io website"
git clone --depth 1 https://github.com/checkstyle/checkstyle.github.io.git

echo "Removing Git metadata and the GitHub Pages CNAME file"
rm -rf checkstyle.github.io/.git
rm -rf checkstyle.github.io/CNAME

echo "Creating the local SourceForge staging directory"
mkdir sourceforge
echo "Downloading the current SourceForge website from $REMOTE_PATH/htdocs/"
rsync --archive --compress --rsh "ssh -i $SSH_KEY_PATH" \
  --include="htdocs/***" --exclude="*" \
  "$SF_USER@web.sourceforge.net:$REMOTE_PATH/" sourceforge/
cd sourceforge

PREV_RELEASE_VERSION=""
if [[ -f htdocs/index.html ]]; then
  echo "Existing SourceForge website found; extracting its release version"
  PREVIOUS_RELEASE_VERSION_SPAN=$(grep "projectVersion" htdocs/index.html)
  REGEX="Version: (.*)<"
  [[ $PREVIOUS_RELEASE_VERSION_SPAN =~ $REGEX ]]
  PREV_RELEASE_VERSION="${BASH_REMATCH[1]}"
  echo "Previous release version: $PREV_RELEASE_VERSION"
  if [[ -z "$PREV_RELEASE_VERSION" ]]
  then
    echo "Problem with extracting previous release version."
    exit 1
  fi

  echo "Saving the existing website as htdocs-$PREV_RELEASE_VERSION"
  mv htdocs "htdocs-$PREV_RELEASE_VERSION"
else
  echo "No existing SourceForge website found; preparing the first deployment"
  rm -rf htdocs
fi

echo "Staging the new website as htdocs"
mv ../checkstyle.github.io htdocs

echo "Adding DTD redirects to htdocs/.htaccess"
cat <<HTACCESS >> htdocs/.htaccess
Redirect 301 "/dtds" "https://checkstyle.org/dtds"
RedirectMatch 301 "/version/.*/dtds/(.*)" "https://checkstyle.org/dtds/\$1"
HTACCESS
chmod o+r htdocs/.htaccess

echo "Linking the website to the SourceForge reports directory"
ln -s "$REMOTE_PATH/reports" htdocs/reports
echo "Removing the DTD files from the SourceForge website"
rm -r htdocs/dtds

if [[ -n "$PREV_RELEASE_VERSION" ]]; then
  echo "Restoring links to all previously published releases"
  mv "htdocs-$PREV_RELEASE_VERSION/version" htdocs

  echo "Creating the full website archive for release $PREV_RELEASE_VERSION"
  tar cfz "htdocs-$PREV_RELEASE_VERSION.tar.gz" "htdocs-$PREV_RELEASE_VERSION/"
  mkdir htdocs-archive
  mv "htdocs-$PREV_RELEASE_VERSION.tar.gz" htdocs-archive/
  rm -rf "htdocs-$PREV_RELEASE_VERSION/"

  echo "Creating the reduced historical website for release $PREV_RELEASE_VERSION"
  mkdir htdocs-version
  tar -xzvf "htdocs-archive/htdocs-$PREV_RELEASE_VERSION.tar.gz" \
    -C htdocs-version/ --same-owner \
    --exclude="*/apidocs" \
    --exclude="*/xref" --exclude="*/xref-test" --exclude="*/cobertura" --exclude="*/dsm" \
    --exclude="*/api" --exclude="reports" --exclude="jacoco" --exclude="dtds" \
    --exclude="dependency-updates-report.html" --exclude="plugin-updates-report.html" \
    --exclude="jdepend-report.html" --exclude="failsafe-report.html" \
    --exclude="surefire-report.html" \
    --exclude="linkcheck.html" --exclude="findbugs.html" --exclude="taglist.html" \
    --exclude="releasenotes_old_6-0_7-8.html" --exclude="releasenotes_old_1-0_5-9.html" \
    --exclude="dependencies.html"

  echo "Linking release $PREV_RELEASE_VERSION from the live website"
  ln -f -s \
    "$REMOTE_PATH/htdocs-version/htdocs-$PREV_RELEASE_VERSION" \
    "htdocs/version/$PREV_RELEASE_VERSION"
else
  echo "Creating an empty historical release directory for the first deployment"
  mkdir htdocs/version
fi

if [[ -n "$PREV_RELEASE_VERSION" ]]; then
  echo "Uploading the full archive for release $PREV_RELEASE_VERSION to SourceForge"
  rsync --archive --compress --rsh "ssh -i $SSH_KEY_PATH" \
    "htdocs-archive/htdocs-$PREV_RELEASE_VERSION.tar.gz" \
    "$SF_USER@web.sourceforge.net:$REMOTE_PATH/htdocs-archive/"
  echo "Uploading the reduced historical website for release $PREV_RELEASE_VERSION"
  rsync --archive --compress --rsh "ssh -i $SSH_KEY_PATH" \
    htdocs-version "$SF_USER@web.sourceforge.net:$REMOTE_PATH/"
fi
echo "Uploading the new live website to SourceForge"
rsync --archive --compress --rsh "ssh -i $SSH_KEY_PATH" \
  --delete --omit-link-times \
  htdocs/ "$SF_USER@web.sourceforge.net:$REMOTE_PATH/htdocs/"

echo "SourceForge website deployment for release $RELEASE_VERSION completed"

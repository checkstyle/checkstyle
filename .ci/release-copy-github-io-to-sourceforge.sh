#!/usr/bin/env bash

set -e

source ./.ci/util.sh

RELEASE=$1

echo "RELEASE version: $RELEASE"

if [[ -z $RELEASE ]]; then
  echo "Problem to calculate release version."
  exit 1
fi

checkForVariable "SF_USER"

SSH_KEY="~/.ssh/private_sourceforge_key"
REMOTE_HOST="web.sourceforge.net"
REMOTE_PATH="/home/project-web/checkstyle"

echo "Creating .ci-temp if it does not exist"
mkdir -p .ci-temp
cd .ci-temp
rm -fr checkstyle.github.io
rm -fr sf_workspace

echo "Cloning checkstyle.github.io repo"
git clone https://github.com/checkstyle/checkstyle.github.io.git

echo "Cleaning up git files"
rm -rf checkstyle.github.io/.git
rm -rf checkstyle.github.io/CNAME

echo "Archiving new files"
tar cfz checkstyle.github.io.tar.gz checkstyle.github.io

# Create a local staging environment
mkdir -p sf_workspace
mv checkstyle.github.io.tar.gz sf_workspace/
cd sf_workspace

echo "Step 1: Downloading current production assets and index.html using rsync"
# We use rsync to pull down the existing site structure from the server
rsync -e "ssh -i $SSH_KEY" -az "$SF_USER"@$REMOTE_HOST:$REMOTE_PATH/htdocs ./htdocs_old

echo "Extracting previous release version locally"
PREVIOUS_RELEASE_VERSION_SPAN=$(grep "projectVersion" htdocs_old/index.html || true)
REGEX="Version: (.*)<"
if [[ $PREVIOUS_RELEASE_VERSION_SPAN =~ $REGEX ]]; then
  PREV_RELEASE="${BASH_REMATCH[1]}"
else
  echo "Problem calculating previous release version from local index.html."
  exit 1
fi
echo "PREVIOUS RELEASE version: $PREV_RELEASE"

echo "Step 2: Performing all HTML swapping and archiving locally"
tar -xzvf checkstyle.github.io.tar.gz
mv htdocs_old htdocs-$PREV_RELEASE
mv checkstyle.github.io htdocs

echo "Creating .htaccess for dtds redirection"
cat <<HTACCESS >> htdocs/.htaccess
Redirect 301 "/dtds" "https://checkstyle.org/dtds"
RedirectMatch 301 "/version/.*/dtds/(.*)" "https://checkstyle.org/dtds/\$1"
HTACCESS
chmod o+r htdocs/.htaccess

# Create the reports symlink locally.
# Rsync will preserve this relative/absolute link direction when pushed to the server.
ln -s /home/project-web/checkstyle/reports htdocs/reports

echo "Removing dtds folder from unsecure web site"
rm -rf htdocs/dtds

echo "Restoring folder with links to old releases"
if [ -d "htdocs-$PREV_RELEASE/version" ]; then
  mv htdocs-$PREV_RELEASE/version htdocs/
else
  mkdir -p htdocs/version
fi

echo "Archiving older version release"
tar cfz htdocs-$PREV_RELEASE.tar.gz htdocs-$PREV_RELEASE/
mkdir -p htdocs-archive
mv htdocs-$PREV_RELEASE.tar.gz htdocs-archive/

echo "Extracting archive to previous releases documentation"
mkdir -p htdocs-version
tar -xzvf htdocs-archive/htdocs-$PREV_RELEASE.tar.gz -C htdocs-version/ \
--exclude="*/apidocs" \
--exclude="*/xref" --exclude="*/xref-test" --exclude="*/cobertura" --exclude="*/dsm" \
--exclude="*/api" --exclude="reports" --exclude="jacoco" --exclude="dtds" \
--exclude="dependency-updates-report.html" --exclude="plugin-updates-report.html" \
--exclude="jdepend-report.html" --exclude="failsafe-report.html" \
--exclude="surefire-report.html" \
--exclude="linkcheck.html" --exclude="findbugs.html" --exclude="taglist.html" \
--exclude="releasenotes_old_6-0_7-8.html" --exclude="releasenotes_old_1-0_5-9.html" \
--exclude="dependencies.html"

# Move the filtered structural archive components to our actual version structure
mv htdocs-version/htdocs-$PREV_RELEASE htdocs/version/$PREV_RELEASE

echo "Step 3: Syncing changes back to SourceForge using rsync"
# --delete ensures files removed locally (like /dtds) are also removed remotely
# -a (archive) preserves symlinks, permissions, and modification times
rsync -e "ssh -i $SSH_KEY" -az --delete htdocs/ "$SF_USER"@$REMOTE_HOST:$REMOTE_PATH/htdocs/

# Sync the archive file directory up as well
mkdir -p local_archive_sync
mv htdocs-archive/htdocs-$PREV_RELEASE.tar.gz local_archive_sync/
rsync -e "ssh -i $SSH_KEY" -az local_archive_sync/ "$SF_USER"@$REMOTE_HOST:$REMOTE_PATH/htdocs-archive/

# Clean up local working directories
cd ../..
rm -rf .ci-temp

echo "Deploy complete via Rsync!"

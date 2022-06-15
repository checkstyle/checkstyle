#!/bin/bash
set -e
date=$(date +"%m%d%y")

# Copying release-settings.xml
mkdir -p .ci-temp
cp ./.ci/release-settings.xml .ci-temp/

# Putting values of environment variables in .ci-temp/release-settings.xml
replace() {
  sed -i "s/$1/$2/g" .ci-temp/release-settings.xml
}

  replace SONATYPE_USER romanivanov
  replace SONATYPE_PWD password
  replace SOURCEFORGE_USER romanivanov
  replace SOURCEFORGE_PWD password
  replace GPG_PASSPHRASE release
  replace GPG_KEY 12345

# Backup of setting.xml
file1="./.ci/release-settings.xml"
file2="~/.m2/setting.xml"

if cmp -s "$file1" "$file2"; then
  ~/.m2/settigns.xml | sudo tee -a ~/.m2/settings.xml.backup."${date}" 2>&1
fi

# Copying generated file to .ci-temp
cp .ci-temp/release-settings.xml ~/.m2/settings.xml

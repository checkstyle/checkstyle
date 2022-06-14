#!/bin/bash
set -e

mkdir -p .ci-temp
cp .ci/release-settings.xml .ci-temp/

checkForVariable() {
  if [[ "$(declare -p "$1")" =~ ^declare\ -x ]]; then
    echo "Error: Define $1 environment variable"
    exit 1
  fi

checkForVariable "SONATYPE_USER"
checkForVariable "SONATYPE_PWD"
checkForVariable "SOURCEFORGE_USER"
checkForVariable "SOURCEFORGE_PWD"
checkForVariable "GPG_PASSPHRASE"
checkForVariable "GPG_KEY"

replace() {
  sed -i "s/$1/$2/g" .ci-temp/release-settings.xml
}

replace SONATYPE_USER
replace SONATYPE_PWD
replace SOURCEFORGE_USER
replace SOURCEFORGE_PWD
replace GPG_PASSPHRASE
replace GPG_KEY
}

file1="./.ci-temp/release-settings.xml"
file2="~/.m2/settings.xml"

if cmp -s "$file1" "$file2"; then
  date=$(date +"%m%d%y")
 mv ~/.m2/settings.xml ~/.m2/settings.xml.backup."${date}"
fi

cp .ci-temp/release-settings.xml ~/.m2/settings.xml

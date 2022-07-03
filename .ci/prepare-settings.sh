#!/bin/bash
set -e

mkdir -p .ci-temp
cp .ci/release-settings.xml .ci-temp/

checkForVariable() {
  VAR_NAME=$1
  if [ -v ${!VAR_NAME} ]; then
    echo "Error: Define $1 environment variable"
    exit 1
  fi
}

checkForVariable "SONATYPE_USER"
checkForVariable "SONATYPE_PWD"
checkForVariable "GPG_PASSPHRASE"
checkForVariable "GPG_KEY"

replace() {
  sed -i "s/$1/$2/g" .ci-temp/release-settings.xml
}

replace SONATYPE_USER
replace SONATYPE_PWD
replace GPG_PASSPHRASE
replace GPG_KEY


TEMP_SETTING="./.ci-temp/release-settings.xml"
SETTING="~/.m2/settings.xml"

if cmp -s "$TEMP_SETTING" "$SETTING"; then
  TODAY=$(date + "%y%m%d")
  mv $SETTING $SETTING.backup."${TODAY}"
fi

cp $TEMP_SETTING $SETTING

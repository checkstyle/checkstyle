#!/bin/bash
set -e

source ./.ci/util.sh

mkdir -p .ci-temp
cp config/release-settings.xml .ci-temp/

checkForVariable "SONATYPE_USER"
checkForVariable "SONATYPE_PWD"
checkForVariable "GPG_PASSPHRASE"
checkForVariable "GPG_KEYNAME"

replace() {
  sed -i "s/$1/$2/g" .ci-temp/release-settings.xml
}

replace SONATYPE_USER "$SONATYPE_USER"
replace SONATYPE_PWD "$SONATYPE_PWD"
replace GPG_PASSPHRASE "$GPG_PASSPHRASE"
replace GPG_KEYNAME "$GPG_KEYNAME"

mkdir -p ~/.m2
TEMP_SETTING=./.ci-temp/release-settings.xml
SETTING=~/.m2/settings.xml

if [ -f "$SETTING" ]; then
  echo "file $SETTING already exists"
  if ! cmp -s "$TEMP_SETTING" "$SETTING"; then
    NOW=$( date '+%Y%m%d%H%M%S' )
    echo "backup $SETTING to $SETTING.backup.${NOW}"
    mv "$SETTING" "$SETTING.backup.${NOW}"
  else
    echo "no backup is required as content is same"
  fi
fi

cp "$TEMP_SETTING" "$SETTING"

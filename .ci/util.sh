#!/bin/bash
# This script contains common bash CI functions
set -e

removeFolderWithProtectedFiles() {
  find $1 -delete
}

function getMavenProperty {
  property="\${$1}"
  echo "$(mvn -e --no-transfer-progress -q -Dexec.executable='echo' \
                      -Dexec.args="${property}" \
                      --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)"
}

function getCheckstylePomVersion {
  getMavenProperty project.version
}

function checkout_from {
  CLONE_URL=$1
  PROJECT=$(echo "$CLONE_URL" | sed -nE 's/.*\/(.*).git/\1/p')
  mkdir -p .ci-temp
  cd .ci-temp
  if [ -d "$PROJECT" ]; then
    echo "Target project $PROJECT is already cloned, latest changes will be fetched"
    cd $PROJECT
    git fetch
    cd ../
  else
    for i in 1 2 3 4 5; do git clone $CLONE_URL && break || sleep 15; done
  fi
  cd ../
}

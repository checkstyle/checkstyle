#!/bin/bash
# This script contains common bash CI functions
set -e

removeFolderWithProtectedFiles() {
  find $1 -delete
}

function getCheckstylePomVersion {
  CS_VERSION_FILE='.ci-temp/cs_version_file'
  mvn -e --no-transfer-progress -q -Dexec.executable='echo' \
                        -Dexec.args='${project.version}' \
                        --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec \
                         > $CS_VERSION_FILE
  # checks last command error code
  if [[ $? != 0 ]]
  then
    cat $CS_VERSION_FILE >&2
    echo "--------------------------------------------" >&2
    echo "| Attempt to get checkstyle version failed |" >&2
    echo "--------------------------------------------" >&2
    exit 1
  fi
  echo $(cat $CS_VERSION_FILE)
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

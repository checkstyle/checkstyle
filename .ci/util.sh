#!/bin/bash
# This script contains common bash CI functions
set -e

removeFolderWithProtectedFiles() {
  find "$1" -delete
}

function checkForVariable() {
  VAR_NAME=$1
  if [ ! -v "$VAR_NAME" ]; then
    echo "Error: Define $1 environment variable"
    exit 1
  fi

  VAR_VALUE="${!VAR_NAME}"
  if [ -z "$VAR_VALUE" ]; then
    echo "Error: Set not empty value to $1 environment variable"
    exit 1
  fi
}

function getMavenProperty {
  property="\${$1}"
  mvn -e --no-transfer-progress -q -Dexec.executable='echo' \
                      -Dexec.args="${property}" \
                      --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec
}

function getCheckstylePomVersion {
  getMavenProperty project.version
}

function getCheckstylePomVersionWithoutSnapshot {
  getCheckstylePomVersion | sed "s/-SNAPSHOT//"
}

function getCheckstylePomVersionWithoutSnapshotWithXmlstarlet {
  xmlstarlet sel -N pom=http://maven.apache.org/POM/4.0.0 \
    -t -m pom:project -v pom:version pom.xml | sed "s/-SNAPSHOT//"
}

function checkout_from {
  CLONE_URL=$1
  PROJECT=$(echo "$CLONE_URL" | sed -nE 's/.*\/(.*).git/\1/p')
  mkdir -p .ci-temp
  cd .ci-temp
  if [ -d "$PROJECT" ]; then
    echo "Target project $PROJECT is already cloned, latest changes will be fetched and reset"
    cd "$PROJECT"
    git fetch
    git reset --hard HEAD
    git clean -f -d
    cd ../
  else
    for i in 1 2 3 4 5; do git clone "$CLONE_URL" && break || sleep 15s; done
  fi
  cd ../
}

function run_with_timeout {
    local TIMEOUT_DURATION=$1
    shift
    local COMMAND="$*"

    # Run the command with a timeout
    if timeout "$TIMEOUT_DURATION" "$COMMAND" ; then
        echo "COMMAND executed successfully within the time limit."
    else
        echo "Error: COMMAND execution took longer than $TIMEOUT_DURATION seconds."
        exit 1
    fi
}

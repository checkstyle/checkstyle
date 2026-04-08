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
  ./mvnw -e --no-transfer-progress -q -Dexec.executable='echo' \
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
  PROJECT=$(basename "$CLONE_URL")
  PROJECT=${PROJECT%.git}

  if [ -z "$PROJECT" ]; then
    echo "Error: cannot determine project folder from URL '$CLONE_URL'"
    exit 1
  fi

  mkdir -p .ci-temp
  cd .ci-temp

  if [ -d "$PROJECT" ]; then
    echo "Target project $PROJECT is already cloned, latest changes will be fetched and reset"
    cd "$PROJECT"
    git fetch --depth 1 origin HEAD
    git reset --hard FETCH_HEAD
    git clean -f -d
    cd ../
  else
    CLONE_SUCCESS=0
    for i in 1 2 3 4 5; do
      git clone --depth 1 "$CLONE_URL" "$PROJECT" && CLONE_SUCCESS=1 && break
      sleep 15s
    done
    if [ "$CLONE_SUCCESS" -ne 1 ]; then
      echo "Error: failed to clone '$CLONE_URL' after 5 attempts"
      exit 1
    fi
  fi

  cd ../
}

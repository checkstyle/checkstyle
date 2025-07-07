#!/bin/bash
# Attention, there is no "-x" to avoid problems on Travis
set -e

source ./.ci/util.sh

export RUN_JOB=1

case $1 in

init-m2-repo)
  if [[ $RUN_JOB == 1 ]]; then
    MVN_REPO=$(mvn -e --no-transfer-progress help:evaluate -Dexpression=settings.localRepository \
      -q -DforceStdout);
    echo "Maven Repo Located At: " "$MVN_REPO"
    MVN_SETTINGS=${TRAVIS_HOME}/.m2/settings.xml
    if [[ -f ${MVN_SETTINGS} ]]; then
      if [[ $TRAVIS_OS_NAME == 'osx' ]]; then
        sed -i'' -e "/<mirrors>/,/<\/mirrors>/ d" "$MVN_SETTINGS"
      else
        xmlstarlet ed --inplace -d "//mirrors" "$MVN_SETTINGS"
      fi
    fi
    if [[ $USE_MAVEN_REPO == 'true' && ! -d "$HOME/.m2" ]]; then
     echo "Maven local repo cache and Maven Wrapper cache is not found, initializing it ..."
     ./mvnw -e --no-transfer-progress -B install -Pno-validations;
     ./mvnw -e --no-transfer-progress clean;
    fi
  else
    echo "$1 is skipped";
  fi
  ;;

run-command)
  if [[ $RUN_JOB == 1 ]]; then
    echo "eval of CMD is starting";
    echo "CMD=$2";
    eval "$2";
    echo "eval of CMD is completed";
  fi
  ;;

run-command-after-success)
  if [[ -n $CMD_AFTER_SUCCESS
        && $RUN_JOB == 1
     ]];
  then
      echo "CMD_AFTER_SUCCESS is starting";
      eval "$CMD_AFTER_SUCCESS";
      echo "CMD_AFTER_SUCCESS is finished";
  fi
  ;;

quarterly-cache-cleanup)
  MVN_REPO=$(./mvnw -e --no-transfer-progress help:evaluate -Dexpression=settings.localRepository \
    -q -DforceStdout);
  if [[ -d ${MVN_REPO} ]]; then
    find "$MVN_REPO" -maxdepth 4 -type d -mtime +90 -exec rm -rf {} \; || true;
  else
    echo "Failed to find correct maven cache to clean. Quietly exiting."
  fi
  ;;

*)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac

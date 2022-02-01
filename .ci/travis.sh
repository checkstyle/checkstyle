#!/bin/bash
# Attention, there is no "-x" to avoid problems on Travis
set -e

source ./.ci/util.sh

export RUN_JOB=1

case $1 in

init-m2-repo)
  if [[ $RUN_JOB == 1 ]]; then
    MVN_SETTINGS=${TRAVIS_HOME}/.m2/settings.xml
    if [[ -f ${MVN_SETTINGS} ]]; then
      if [[ $TRAVIS_OS_NAME == 'osx' ]]; then
        sed -i'' -e "/<mirrors>/,/<\/mirrors>/ d" $MVN_SETTINGS
      else
        xmlstarlet ed --inplace -d "//mirrors" $MVN_SETTINGS
      fi
    fi
    if [[ $USE_MAVEN_REPO == 'true' && ! -d "~/.m2" ]]; then
     echo "Maven local repo cache is not found, initializing it ..."
     mvn -e --no-transfer-progress -B install -Pno-validations;
     mvn -e --no-transfer-progress clean;
    fi
  else
    echo "$1 is skipped";
  fi
  ;;

install-custom-mvn)
  if [[ -n "${CUSTOM_MVN_VERSION}" ]]; then
    echo "Download Maven ${CUSTOM_MVN_VERSION}....";
    URL="https://archive.apache.org/dist/maven/maven-3/"
    URL=$URL"${CUSTOM_MVN_VERSION}/binaries/apache-maven-${CUSTOM_MVN_VERSION}-bin.zip"
    wget $URL
    unzip -q apache-maven-${CUSTOM_MVN_VERSION}-bin.zip
    export M2_HOME=$PWD/apache-maven-${CUSTOM_MVN_VERSION};
    export PATH=$M2_HOME/bin:$PATH;
  fi
  ;;

remove-custom-mvn)
  if [[ -n "${CUSTOM_MVN_VERSION}" ]]; then
    rm apache-maven-${CUSTOM_MVN_VERSION}-bin.zip
    rm -rf apache-maven-${CUSTOM_MVN_VERSION}
  fi
  ;;

run-command)
  if [[ $RUN_JOB == 1 ]]; then
    echo "eval of CMD is starting";
    echo "CMD=$2";
    eval $2;
    echo "eval of CMD is completed";
  fi
  ;;

run-command-after-success)
  if [[ -n $CMD_AFTER_SUCCESS
        && $RUN_JOB == 1
     ]];
  then
      echo "CMD_AFTER_SUCCESS is starting";
      eval $CMD_AFTER_SUCCESS;
      echo "CMD_AFTER_SUCCESS is finished";
  fi
  ;;

deploy-snapshot)
  SKIP_DEPLOY=false
  if [ $(git log -1 | grep -E "\[maven-release-plugin\] prepare release" | cat | wc -l) -lt 1 ];
    then
      SKIP_DEPLOY=false;
    else
      SKIP_DEPLOY=true;
  fi;
  if [[ $TRAVIS_REPO_SLUG == 'checkstyle/checkstyle'
          && $TRAVIS_BRANCH == 'master'
          && $TRAVIS_PULL_REQUEST == 'false'
          && $DEPLOY == 'true'
          && $RUN_JOB == 1
          && $SKIP_DEPLOY == 'false'
     ]];
  then
      mvn -e --no-transfer-progress -s config/deploy-settings.xml -Pno-validations deploy;
      echo "deploy to maven snapshot repository is finished";
  fi
  sleep 5s
  ;;

git-diff)
  if [ "$(git status | grep 'Changes not staged\|Untracked files')" ]; then
    printf "Please clean up or update .gitattributes file.\nGit status output:\n"
    git status
    printf "Top 300 lines of diff:\n"
    git diff | head -n 300
    sleep 5s
    false
  fi
  ;;

ci-temp-check)
    fail=0
    mkdir -p .ci-temp
    if [ -z "$(ls -A .ci-temp)" ]; then
        echo "Folder .ci-temp/ is empty."
    else
        echo "Folder .ci-temp/ is not empty. Verification failed."
        echo "Contents of .ci-temp/:"
        fail=1
    fi
    ls -A .ci-temp
    sleep 5s
    exit $fail
  ;;

jacoco)
  export MAVEN_OPTS='-Xmx2000m'
  mvn -e --no-transfer-progress clean test \
    jacoco:restore-instrumented-classes \
    jacoco:report@default-report \
    jacoco:check@default-check
  # BUILD_REASON is variable from CI, if launch is not from CI, we skip this step
  if [ -n "$BUILD_REASON" ];then
    bash <(curl -s https://codecov.io/bash)
  fi
  ;;

*)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac

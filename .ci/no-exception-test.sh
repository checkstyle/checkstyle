#!/bin/bash
set -e

removeFolderWithProtectedFiles() {
  find $1 -delete
}

function getCheckstylePomVersion {
  echo "$(mvn -e --no-transfer-progress -q -Dexec.executable='echo' \
                      -Dexec.args='${project.version}' \
                      --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)"
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

case $1 in

guava-with-google-checks)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: $CS_POM_VERSION
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#guava|/guava|/' projects-to-test-on.properties
  cd ../../../
  mvn -e --no-transfer-progress clean install -Pno-validations
  cp src/main/resources/google_checks.xml .ci-temp/google_checks.xml
  sed -i.'' 's/warning/ignore/' .ci-temp/google_checks.xml
  cd .ci-temp/contribution/checkstyle-tester
  export MAVEN_OPTS="-Xmx2048m"
  groovy ./launch.groovy --listOfProjects projects-to-test-on.properties \
      --config ../../google_checks.xml --checkstyleVersion $CS_POM_VERSION
  cd ../..
  removeFolderWithProtectedFiles contribution
  rm google_checks.*
  ;;

guava-with-sun-checks)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: $CS_POM_VERSION
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#guava|/guava|/' projects-to-test-on.properties
  cd ../../../
  mvn -e --no-transfer-progress clean install -Pno-validations
  cp src/main/resources/sun_checks.xml .ci-temp/sun_checks.xml
  sed -i.'' 's/value=\"error\"/value=\"ignore\"/' .ci-temp/sun_checks.xml
  cd .ci-temp/contribution/checkstyle-tester
  export MAVEN_OPTS="-Xmx2048m"
  groovy ./launch.groovy --listOfProjects projects-to-test-on.properties \
      --config ../../sun_checks.xml --checkstyleVersion $CS_POM_VERSION
  cd ../..
  removeFolderWithProtectedFiles contribution
  rm sun_checks.*
  ;;

openjdk14-with-checks-nonjavadoc-error)
  LOCAL_GIT_REPO=$(pwd)
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  checkout_from https://github.com/checkstyle/contribution
  sed -i.'' 's/value=\"error\"/value=\"ignore\"/' \
        .ci-temp/contribution/checkstyle-tester/checks-nonjavadoc-error.xml
  cd .ci-temp/contribution/checkstyle-tester
  cp ../../../.ci/openjdk-projects-to-test-on.config openjdk-projects-to-test-on.config
  sed -i '/  <!-- Filters -->/r ../../../.ci/openjdk14-excluded.files' checks-nonjavadoc-error.xml
  export MAVEN_OPTS="-Xmx2048m"
  groovy ./diff.groovy --listOfProjects openjdk-projects-to-test-on.config \
      --mode single \
      --patchConfig checks-nonjavadoc-error.xml \
      --localGitRepo  "$LOCAL_GIT_REPO" \
      --patchBranch "$BRANCH"

  cd ../../
  removeFolderWithProtectedFiles contribution
  ;;

  *)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac

#!/bin/bash
set -e

source ./.ci/util.sh

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
  groovy ./diff.groovy --listOfProjects projects-to-test-on.properties \
      --patchConfig ../../google_checks.xml \
      --mode single -xm "-Dcheckstyle.failsOnError=false \
      -Dcheckstyle.version=${CS_POM_VERSION}" -p master -r ../../..
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
  groovy ./diff.groovy --listOfProjects projects-to-test-on.properties \
      --patchConfig ../../sun_checks.xml \
      --mode single -xm "-Dcheckstyle.failsOnError=false \
      -Dcheckstyle.version=${CS_POM_VERSION}"  -p master -r ../../..
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
      --patchBranch "$BRANCH" -xm "-Dcheckstyle.failsOnError=false"

  cd ../../
  removeFolderWithProtectedFiles contribution
  ;;

  *)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac

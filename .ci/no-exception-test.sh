#!/bin/bash
set -e

removeFolderWithProtectedFiles() {
  find $1 -delete
}

case $1 in

guava-with-google-checks)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: $CS_POM_VERSION
  mkdir -p .ci-temp/
  cd .ci-temp/
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#guava|/guava|/' projects-to-test-on.properties
  cd ../../../
  mvn -e clean install -Pno-validations
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
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: $CS_POM_VERSION
  mkdir -p .ci-temp/
  cd .ci-temp/
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#guava|/guava|/' projects-to-test-on.properties
  cd ../../../
  mvn -e clean install -Pno-validations
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

  *)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac

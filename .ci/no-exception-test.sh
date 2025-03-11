#!/bin/bash
set -e

source ./.ci/util.sh

case $1 in

guava-with-google-checks)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  echo CS_version: "$CS_POM_VERSION"
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#guava|/guava|/' projects-to-test-on.properties
  cd ../../../
  cp src/main/resources/google_checks.xml .ci-temp/google_checks.xml
  sed -i.'' 's/warning/ignore/' .ci-temp/google_checks.xml
  cd .ci-temp/contribution/checkstyle-tester
  export MAVEN_OPTS="-Xmx2048m"
  groovy ./diff.groovy --listOfProjects projects-to-test-on.properties \
      --patchConfig ../../google_checks.xml \
      --mode single --allowExcludes -xm "-Dcheckstyle.failsOnError=false" \
      -p "$BRANCH" -r ../../..
  cd ../..
  removeFolderWithProtectedFiles contribution
  rm google_checks.*
  ;;

guava-with-sun-checks)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  echo CS_version: "$CS_POM_VERSION"
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#guava|/guava|/' projects-to-test-on.properties
  cd ../../../
  cp src/main/resources/sun_checks.xml .ci-temp/sun_checks.xml
  sed -i.'' 's/value=\"error\"/value=\"ignore\"/' .ci-temp/sun_checks.xml
  cd .ci-temp/contribution/checkstyle-tester
  export MAVEN_OPTS="-Xmx2048m"
  groovy ./diff.groovy --listOfProjects projects-to-test-on.properties \
      --patchConfig ../../sun_checks.xml \
      --mode single --allowExcludes -xm "-Dcheckstyle.failsOnError=false" \
      -p "$BRANCH" -r ../../..
  cd ../..
  removeFolderWithProtectedFiles contribution
  rm sun_checks.*
  ;;

openjdk17-with-checks-nonjavadoc-error)
  LOCAL_GIT_REPO=$(pwd)
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  checkout_from https://github.com/checkstyle/contribution
  sed -i.'' 's/value=\"error\"/value=\"ignore\"/' \
        .ci-temp/contribution/checkstyle-tester/checks-nonjavadoc-error.xml
  cd .ci-temp/contribution/checkstyle-tester
  cp ../../../config/projects-to-test/openjdk-17-projects-to-test-on.config \
      openjdk-17-projects-to-test-on.config
  sed -i '/  <!-- Filters -->/r ../../../config/projects-to-test/openjdk17-excluded.files' \
      checks-nonjavadoc-error.xml
  export MAVEN_OPTS="-Xmx2048m"
  groovy ./diff.groovy --listOfProjects openjdk-17-projects-to-test-on.config \
      --mode single --allowExcludes \
      --patchConfig checks-nonjavadoc-error.xml \
      --localGitRepo  "$LOCAL_GIT_REPO" \
      --patchBranch "$BRANCH" -xm "-Dcheckstyle.failsOnError=false"

  cd ../../
  removeFolderWithProtectedFiles contribution
  ;;

openjdk19-with-checks-nonjavadoc-error)
  LOCAL_GIT_REPO=$(pwd)
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  checkout_from https://github.com/checkstyle/contribution
  sed -i.'' 's/value=\"error\"/value=\"ignore\"/' \
        .ci-temp/contribution/checkstyle-tester/checks-nonjavadoc-error.xml
  cd .ci-temp/contribution/checkstyle-tester
  cp ../../../config/projects-to-test/openjdk-19-projects-to-test-on.config \
      openjdk-19-projects-to-test-on.config
  sed -i '/  <!-- Filters -->/r ../../../config/projects-to-test/openjdk19-excluded.files' \
      checks-nonjavadoc-error.xml
  export MAVEN_OPTS="-Xmx2048m"
  groovy ./diff.groovy --listOfProjects openjdk-19-projects-to-test-on.config \
      --mode single --allowExcludes \
      --patchConfig checks-nonjavadoc-error.xml \
      --localGitRepo  "$LOCAL_GIT_REPO" \
      --patchBranch "$BRANCH" -xm "-Dcheckstyle.failsOnError=false"

  cd ../../
  removeFolderWithProtectedFiles contribution
  ;;

openjdk20-with-checks-nonjavadoc-error)
  LOCAL_GIT_REPO=$(pwd)
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  checkout_from https://github.com/checkstyle/contribution
  sed -i.'' 's/value=\"error\"/value=\"ignore\"/' \
        .ci-temp/contribution/checkstyle-tester/checks-nonjavadoc-error.xml
  cd .ci-temp/contribution/checkstyle-tester
  cp ../../../config/projects-to-test/openjdk-20-projects-to-test-on.config \
      openjdk-20-projects-to-test-on.config
  sed -i '/  <!-- Filters -->/r ../../../config/projects-to-test/openjdk20-excluded.files' \
      checks-nonjavadoc-error.xml
  export MAVEN_OPTS="-Xmx2048m"
  groovy ./diff.groovy --listOfProjects openjdk-20-projects-to-test-on.config \
      --mode single --allowExcludes \
      --patchConfig checks-nonjavadoc-error.xml \
      --localGitRepo  "$LOCAL_GIT_REPO" \
      --patchBranch "$BRANCH" -xm "-Dcheckstyle.failsOnError=false"

  cd ../../
  removeFolderWithProtectedFiles contribution
  ;;

openjdk21-with-checks-nonjavadoc-error)
  LOCAL_GIT_REPO=$(pwd)
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  checkout_from https://github.com/checkstyle/contribution
  sed -i.'' 's/value=\"error\"/value=\"ignore\"/' \
        .ci-temp/contribution/checkstyle-tester/checks-nonjavadoc-error.xml
  cd .ci-temp/contribution/checkstyle-tester
  cp ../../../config/projects-to-test/openjdk-21-projects-to-test-on.config \
      openjdk-21-projects-to-test-on.config
  sed -i '/  <!-- Filters -->/r ../../../config/projects-to-test/openjdk21-excluded.files' \
      checks-nonjavadoc-error.xml
  export MAVEN_OPTS="-Xmx2048m"
  groovy ./diff.groovy --listOfProjects openjdk-21-projects-to-test-on.config \
      --mode single --allowExcludes \
      --patchConfig checks-nonjavadoc-error.xml \
      --localGitRepo  "$LOCAL_GIT_REPO" \
      --patchBranch "$BRANCH" -xm "-Dcheckstyle.failsOnError=false"

  cd ../../
  removeFolderWithProtectedFiles contribution
  ;;

no-exception-lucene-and-others-javadoc)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  echo 'CS_POM_VERSION='"${CS_POM_VERSION}"
  checkout_from https://github.com/checkstyle/contribution
  cp config/projects-to-test/projects-for-no-exception-javadoc.config \
      .ci-temp/contribution/checkstyle-tester
  cd .ci-temp/contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-for-no-exception-javadoc.config
  sed -i'' 's/#infinispan/infinispan/' projects-for-no-exception-javadoc.config
  sed -i'' 's/#protonpack/protonpack/' projects-for-no-exception-javadoc.config
  sed -i'' 's/#jOOL/jOOL/' projects-for-no-exception-javadoc.config
  sed -i'' 's/#lucene-solr/lucene-solr/' projects-for-no-exception-javadoc.config
  export MAVEN_OPTS="-Xmx2048m"
  groovy ./diff.groovy --listOfProjects projects-for-no-exception-javadoc.config \
      --patchConfig checks-only-javadoc-error.xml \
      --mode single --allowExcludes -xm "-Dcheckstyle.failsOnError=false" \
      -p "$BRANCH" -r ../../..
  cd ../..
  removeFolderWithProtectedFiles contribution
  ;;

no-exception-cassandra-storm-tapestry-javadoc)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo 'CS_POM_VERSION='"${CS_POM_VERSION}"
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  checkout_from https://github.com/checkstyle/contribution
  cp config/projects-to-test/projects-for-no-exception-javadoc.config \
      .ci-temp/contribution/checkstyle-tester
  cd .ci-temp/contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-for-no-exception-javadoc.config
  sed -i'' 's/#tapestry-5/tapestry-5/' projects-for-no-exception-javadoc.config
  sed -i'' 's/#storm/storm/' projects-for-no-exception-javadoc.config
  sed -i'' 's/#cassandra/cassandra/' projects-for-no-exception-javadoc.config
  export MAVEN_OPTS="-Xmx2048m"
  groovy ./diff.groovy --listOfProjects projects-for-no-exception-javadoc.config \
      --patchConfig checks-only-javadoc-error.xml \
      --mode single --allowExcludes  -xm "-Dcheckstyle.failsOnError=false" \
      -p "$BRANCH" -r ../../..
  cd ../..
  removeFolderWithProtectedFiles contribution
  ;;

no-exception-hadoop-apache-groovy-scouter-javadoc)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo 'CS_POM_VERSION='"${CS_POM_VERSION}"
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  checkout_from https://github.com/checkstyle/contribution
  cp config/projects-to-test/projects-for-no-exception-javadoc.config \
      .ci-temp/contribution/checkstyle-tester
  cd .ci-temp/contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-for-no-exception-javadoc.config
  sed -i'' 's/#apache-commons/apache-commons/' projects-for-no-exception-javadoc.config
  sed -i'' 's/#hadoop/hadoop/' projects-for-no-exception-javadoc.config
  sed -i'' 's/#groovy/groovy/' projects-for-no-exception-javadoc.config
  sed -i'' 's/#scouter/scouter/' projects-for-no-exception-javadoc.config
  export MAVEN_OPTS="-Xmx2048m"
  groovy ./diff.groovy --listOfProjects projects-for-no-exception-javadoc.config \
      --patchConfig checks-only-javadoc-error.xml \
      --mode single --allowExcludes -xm "-Dcheckstyle.failsOnError=false" \
      -p "$BRANCH" -r ../../..
  cd ../..
  removeFolderWithProtectedFiles contribution
  ;;

no-exception-only-javadoc)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo 'CS_POM_VERSION='"${CS_POM_VERSION}"
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#spring-framework/spring-framework/' projects-to-test-on.properties
  sed -i.'' 's/#nbia-dcm4che-tools/nbia-dcm4che-tools/' projects-to-test-on.properties
  sed -i.'' 's/#spotbugs/spotbugs/' projects-to-test-on.properties
  sed -i.'' 's/#pmd/pmd/' projects-to-test-on.properties
  sed -i.'' 's/#apache-ant/apache-ant/' projects-to-test-on.properties
  export MAVEN_OPTS="-Xmx2048m"
  groovy ./diff.groovy --listOfProjects projects-to-test-on.properties \
      --patchConfig checks-only-javadoc-error.xml --allowExcludes \
      --mode single -xm "-Dcheckstyle.failsOnError=false" \
      -p "$BRANCH" -r ../../..
  cd ../..
  removeFolderWithProtectedFiles contribution
  ;;

no-exception-samples-ant)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo 'CS_POM_VERSION='"${CS_POM_VERSION}"
  mvn -e --no-transfer-progress -B install -Pno-validations
  checkout_from https://github.com/sevntu-checkstyle/checkstyle-samples
  cd .ci-temp/checkstyle-samples/ant-project

  sed -i -e "/<dependencies>/,/<\/dependencies>/ "`
    `"s|name=\"checkstyle\" rev=\".*\""`
    `"|name=\"checkstyle\" rev=\"$CS_POM_VERSION\"|g" ivy.xml

  ant checkstyle

  cd ../..
  removeFolderWithProtectedFiles checkstyle-samples
  ;;

no-exception-samples-gradle)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo 'CS_POM_VERSION='"${CS_POM_VERSION}"
  mvn -e --no-transfer-progress -B install -Pno-validations
  checkout_from https://github.com/sevntu-checkstyle/checkstyle-samples
  cd .ci-temp/checkstyle-samples/gradle-project

  sed -i "s/\(project\.ext\.checkstyleVersion = \)'[0-9.]\+'/\\1'${CS_POM_VERSION}'/" \
    build.gradle

   echo "Checking gradle properties..."
  ./gradlew properties
  ./gradlew check

  cd ../..
  removeFolderWithProtectedFiles checkstyle-samples
  ;;

no-exception-samples-maven)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo 'CS_POM_VERSION='"${CS_POM_VERSION}"
  mvn -e --no-transfer-progress -B install -Pno-validations

  checkout_from https://github.com/sevntu-checkstyle/checkstyle-samples
  cd .ci-temp/checkstyle-samples/maven-project

  sed -i "s|\(<checkstyle.version>\)[0-9.]\+\(</checkstyle.version>\)"`
  `"|\1${CS_POM_VERSION}\2|" pom.xml


  echo "Building Maven project..."
  mvn -e --no-transfer-progress -B verify

  cd ../..
  removeFolderWithProtectedFiles checkstyle-samples
  ;;

  *)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac

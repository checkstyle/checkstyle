#!/bin/bash
# Attention, there is no "-x" to avoid problems on Wercker
set -e

source ./.ci/util.sh

function build_checkstyle {
  if [[ "$SHIPPABLE" == "true" ]]; then
    echo "Build checkstyle ..."
    mvn -e --no-transfer-progress clean install -Pno-validations
  fi
}

case $1 in

no-exception-openjdk7-openjdk8)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo 'CS_POM_VERSION='${CS_POM_VERSION}
  build_checkstyle
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-for-circle.properties
  sed -i'' 's/#openjdk7/openjdk7/' projects-for-circle.properties
  sed -i'' 's/#openjdk8/openjdk8/' projects-for-circle.properties
  groovy diff.groovy --listOfProjects projects-for-circle.properties \
    --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION} \
    --allowExcludes -xm "-Dcheckstyle.failsOnError=false"
  cd ../..
  removeFolderWithProtectedFiles .ci-temp/contribution
  ;;

no-exception-openjdk9-lucene-and-others)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo 'CS_POM_VERSION='${CS_POM_VERSION}
  build_checkstyle
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-for-circle.properties
  # till hg is installed
  # sed -i'' 's/#openjdk9/openjdk9/' projects-for-circle.properties
  sed -i'' 's/#infinispan/infinispan/' projects-for-circle.properties
  sed -i'' 's/#protonpack/protonpack/' projects-for-circle.properties
  sed -i'' 's/#jOOL/jOOL/' projects-for-circle.properties
  sed -i'' 's/#lucene-solr/lucene-solr/' projects-for-circle.properties
  groovy diff.groovy --listOfProjects projects-for-circle.properties \
    --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION} \
    --allowExcludes -xm "-Dcheckstyle.failsOnError=false"
  cd ../..
  removeFolderWithProtectedFiles contribution
  ;;

no-exception-cassandra-storm-tapestry)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo 'CS_POM_VERSION='${CS_POM_VERSION}
  build_checkstyle
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-for-circle.properties
  sed -i'' 's/#tapestry-5/tapestry-5/' projects-for-circle.properties
  sed -i'' 's/#storm/storm/' projects-for-circle.properties
  sed -i'' 's/#cassandra/cassandra/' projects-for-circle.properties
  groovy diff.groovy --listOfProjects projects-for-circle.properties \
    --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION} \
    --allowExcludes -xm "-Dcheckstyle.failsOnError=false"
  cd ../..
  removeFolderWithProtectedFiles contribution
  ;;

no-exception-hadoop-apache-groovy-scouter)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo 'CS_POM_VERSION='${CS_POM_VERSION}
  build_checkstyle
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-for-circle.properties
  sed -i'' 's/#apache-commons/apache-commons/' projects-for-circle.properties
  sed -i'' 's/#hadoop/hadoop/' projects-for-circle.properties
  sed -i'' 's/#groovy/groovy/' projects-for-circle.properties
  sed -i'' 's/#scouter/scouter/' projects-for-circle.properties
  groovy diff.groovy --listOfProjects projects-for-circle.properties \
    --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION} \
    --allowExcludes -xm "-Dcheckstyle.failsOnError=false"
  cd ../..
  removeFolderWithProtectedFiles contribution
  ;;

no-exception-only-javadoc)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo 'CS_POM_VERSION='${CS_POM_VERSION}
  build_checkstyle
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#spring-framework/spring-framework/' projects-to-test-on.properties
  sed -i.'' 's/#nbia-dcm4che-tools/nbia-dcm4che-tools/' projects-to-test-on.properties
  sed -i.'' 's/#spotbugs/spotbugs/' projects-to-test-on.properties
  sed -i.'' 's/#pmd/pmd/' projects-to-test-on.properties
  sed -i.'' 's/#apache-ant/apache-ant/' projects-to-test-on.properties
  groovy diff.groovy --listOfProjects projects-to-test-on.properties \
    --config checks-only-javadoc-error.xml --checkstyleVersion ${CS_POM_VERSION} \
    --allowExcludes -xm "-Dcheckstyle.failsOnError=false"
  cd ../..
  removeFolderWithProtectedFiles contribution
  ;;

validate-ci-temp-empty)
  fail=0
  if [ "$(ls -A .ci-temp)" ]; then
    ls -A .ci-temp
    echo ".ci-temp/ is not empty. Verification failed."
    fail=1
  fi
  exit $fail
  ;;

git-status)
  if [ "$(git status | grep 'Changes not staged\|Untracked files')" ]; then
    printf "Please clean up or update .gitattributes file.\nGit status output:\n"
    git status
    sleep 5s
    false
  fi
  ;;

*)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac

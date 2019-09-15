#!/bin/bash
# Attention, there is no "-x" to avoid problems on Wercker
set -e

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

function build_checkstyle {
  if [[ "$SHIPPABLE" == "true" ]]; then
    echo "Build checkstyle ..."
    mvn clean install -Pno-validations
  fi
}

case $1 in

no-exception-openjdk7-openjdk8)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                      --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo 'CS_POM_VERSION='${CS_POM_VERSION}
  build_checkstyle
  checkout_from https://github.com/checkstyle/contribution.git
  cd .ci-temp/contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-for-circle.properties
  sed -i'' 's/#openjdk7/openjdk7/' projects-for-circle.properties
  sed -i'' 's/#openjdk8/openjdk8/' projects-for-circle.properties
  groovy launch.groovy --listOfProjects projects-for-circle.properties \
    --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION}
  ;;

no-exception-openjdk9-lucene-and-others)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                      --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo 'CS_POM_VERSION='${CS_POM_VERSION}
  build_checkstyle
  checkout_from https://github.com/checkstyle/contribution.git
  cd .ci-temp/contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-for-circle.properties
  # till hg is installed
  #sed -i'' 's/#openjdk9/openjdk9/' projects-for-circle.properties
  sed -i'' 's/#infinispan/infinispan/' projects-for-circle.properties
  sed -i'' 's/#protonpack/protonpack/' projects-for-circle.properties
  sed -i'' 's/#jOOL/jOOL/' projects-for-circle.properties
  sed -i'' 's/#lucene-solr/lucene-solr/' projects-for-circle.properties
  groovy launch.groovy --listOfProjects projects-for-circle.properties \
    --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION}
  ;;

no-exception-cassandra-storm-tapestry)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                      --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo 'CS_POM_VERSION='${CS_POM_VERSION}
  build_checkstyle
  checkout_from https://github.com/checkstyle/contribution.git
  cd .ci-temp/contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-for-circle.properties
  sed -i'' 's/#tapestry-5/tapestry-5/' projects-for-circle.properties
  sed -i'' 's/#storm/storm/' projects-for-circle.properties
  sed -i'' 's/#cassandra/cassandra/' projects-for-circle.properties
  groovy launch.groovy --listOfProjects projects-for-circle.properties \
    --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION}
  ;;

no-exception-hadoop-apache-groovy-scouter)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                      --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo 'CS_POM_VERSION='${CS_POM_VERSION}
  build_checkstyle
  checkout_from https://github.com/checkstyle/contribution.git
  cd .ci-temp/contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-for-circle.properties
  sed -i'' 's/#apache-commons/apache-commons/' projects-for-circle.properties
  sed -i'' 's/#hadoop/hadoop/' projects-for-circle.properties
  sed -i'' 's/#groovy/groovy/' projects-for-circle.properties
  sed -i'' 's/#scouter/scouter/' projects-for-circle.properties
  groovy launch.groovy --listOfProjects projects-for-circle.properties \
    --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION}
  ;;

no-exception-only-javadoc)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                      --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo 'CS_POM_VERSION='${CS_POM_VERSION}
  build_checkstyle
  checkout_from https://github.com/checkstyle/contribution.git
  cd .ci-temp/contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#spring-framework/spring-framework/' projects-to-test-on.properties
  sed -i.'' 's/#nbia-dcm4che-tools/nbia-dcm4che-tools/' projects-to-test-on.properties
  sed -i.'' 's/#spotbugs/spotbugs/' projects-to-test-on.properties
  sed -i.'' 's/#pmd/pmd/' projects-to-test-on.properties
  sed -i.'' 's/#apache-ant/apache-ant/' projects-to-test-on.properties
  groovy launch.groovy --listOfProjects projects-to-test-on.properties \
    --config checks-only-javadoc-error.xml --checkstyleVersion ${CS_POM_VERSION}
  ;;

*)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac

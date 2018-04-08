#!/bin/bash
# Attention, there is no "-x" to avoid problems on Wercker
set -e

case $1 in

no-exception-openjdk7-openjdk8)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo 'CS_POM_VERSION='${CS_POM_VERSION}
  cd ../
  cd contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-for-circle.properties
  sed -i'' 's/#openjdk7/openjdk7/' projects-for-circle.properties
  sed -i'' 's/#openjdk8/openjdk8/' projects-for-circle.properties
  groovy launch.groovy --listOfProjects projects-for-circle.properties \
    --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION}
  ;;

no-exception-openjdk9-lucene-and-others)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo 'CS_POM_VERSION='${CS_POM_VERSION}
  cd ../
  cd contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-for-circle.properties
  sed -i'' 's/#openjdk9/openjdk9/' projects-for-circle.properties
  sed -i'' 's/#infinispan/infinispan/' projects-for-circle.properties
  sed -i'' 's/#protonpack/protonpack/' projects-for-circle.properties
  sed -i'' 's/#jOOL/jOOL/' projects-for-circle.properties
  sed -i'' 's/#lucene-solr/lucene-solr/' projects-for-circle.properties
  groovy launch.groovy --listOfProjects projects-for-circle.properties \
    --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION}
  ;;

no-exception-cassandra-storm-tapestry)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo 'CS_POM_VERSION='${CS_POM_VERSION}
  cd ../
  cd contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-for-circle.properties
  sed -i'' 's/#tapestry-5/tapestry-5/' projects-for-circle.properties
  sed -i'' 's/#storm/storm/' projects-for-circle.properties
  sed -i'' 's/#cassandra/cassandra/' projects-for-circle.properties
  groovy launch.groovy --listOfProjects projects-for-circle.properties \
    --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION}
  ;;

no-exception-hadoop-apache-groovy-scouter)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo 'CS_POM_VERSION='${CS_POM_VERSION}
  cd ../
  cd contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-for-circle.properties
  sed -i'' 's/#apache-commons/apache-commons/' projects-for-circle.properties
  sed -i'' 's/#hadoop/hadoop/' projects-for-circle.properties
  sed -i'' 's/#groovy/groovy/' projects-for-circle.properties
  sed -i'' 's/#scouter/scouter/' projects-for-circle.properties
  groovy launch.groovy --listOfProjects projects-for-circle.properties \
    --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION}
  ;;

*)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac

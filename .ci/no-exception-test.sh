#!/bin/bash
set -e

removeFolderWithProtectedFiles() {
  find $1 -delete
}

case $1 in

checkstyle-sevntu-checkstyle)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                    --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: $CS_POM_VERSION
  mkdir -p .ci-temp/
  cd .ci-temp/
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#checkstyle/checkstyle/' projects-to-test-on.properties
  sed -i.'' 's/#sevntu-checkstyle/sevntu-checkstyle/' projects-to-test-on.properties
  cd ../../../
  mvn -e clean install -Pno-validations
  cd .ci-temp/contribution/checkstyle-tester
  export MAVEN_OPTS="-Xmx2048m"
  groovy ./launch.groovy --listOfProjects projects-to-test-on.properties \
    --config checks-nonjavadoc-error.xml --checkstyleVersion $CS_POM_VERSION
  cd ../..
  removeFolderWithProtectedFiles contribution
  ;;

guava)
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
  cd .ci-temp/contribution/checkstyle-tester
  export MAVEN_OPTS="-Xmx2048m"
  groovy ./launch.groovy --listOfProjects projects-to-test-on.properties \
     --config checks-nonjavadoc-error.xml --checkstyleVersion $CS_POM_VERSION
  cd ../..
  removeFolderWithProtectedFiles contribution
  ;;

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

hibernate)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                    --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: $CS_POM_VERSION
  mkdir -p .ci-temp/
  cd .ci-temp/
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#hibernate-orm/hibernate-orm/' projects-to-test-on.properties
  cd ../../../
  mvn -e clean install -Pno-validations
  cd .ci-temp/contribution/checkstyle-tester
  export MAVEN_OPTS="-Xmx2048m"
  groovy ./launch.groovy --listOfProjects projects-to-test-on.properties \
     --config checks-nonjavadoc-error.xml --checkstyleVersion $CS_POM_VERSION
  cd ../..
  removeFolderWithProtectedFiles contribution
  ;;

spotbugs)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: $CS_POM_VERSION
  mkdir -p .ci-temp/
  cd .ci-temp/
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#spotbugs/spotbugs/' projects-to-test-on.properties
  cd ../../../
  mvn -e clean install -Pno-validations
  cd .ci-temp/contribution/checkstyle-tester
  export MAVEN_OPTS="-Xmx2048m"
  groovy ./launch.groovy --listOfProjects projects-to-test-on.properties \
    --config checks-nonjavadoc-error.xml --checkstyleVersion $CS_POM_VERSION
  cd ../..
  removeFolderWithProtectedFiles contribution
  ;;

spring-framework)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: $CS_POM_VERSION
  mkdir -p .ci-temp/
  cd .ci-temp/
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#spring-framework/spring-framework/' projects-to-test-on.properties
  cd ../../../
  mvn -e clean install -Pno-validations
  cd .ci-temp/contribution/checkstyle-tester
  export MAVEN_OPTS="-Xmx2048m"
  groovy ./launch.groovy --listOfProjects projects-to-test-on.properties \
    --config checks-nonjavadoc-error.xml --checkstyleVersion $CS_POM_VERSION
  cd ../..
  removeFolderWithProtectedFiles contribution
  ;;

hbase)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: $CS_POM_VERSION
  mkdir -p .ci-temp/
  cd .ci-temp/
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#Hbase/Hbase/' projects-to-test-on.properties
  cd ../../../
  mvn -e clean install -Pno-validations
  cd .ci-temp/contribution/checkstyle-tester
  export MAVEN_OPTS="-Xmx2048m"
  groovy ./launch.groovy --listOfProjects projects-to-test-on.properties \
      --config checks-nonjavadoc-error.xml --checkstyleVersion $CS_POM_VERSION
  cd ../..
  removeFolderWithProtectedFiles contribution
  ;;

pmd-elasticsearch-lombok-ast)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: $CS_POM_VERSION
  mkdir -p .ci-temp/
  cd .ci-temp/
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#pmd/pmd/' projects-to-test-on.properties
  sed -i.'' 's/#elasticsearch/elasticsearch/' projects-to-test-on.properties
  sed -i.'' 's/#lombok-ast/lombok-ast/' projects-to-test-on.properties
  cd ../../../
  mvn -e clean install -Pno-validations
  cd .ci-temp/contribution/checkstyle-tester
  export MAVEN_OPTS="-Xmx2048m"
  groovy ./launch.groovy --listOfProjects projects-to-test-on.properties \
      --config checks-nonjavadoc-error.xml --checkstyleVersion $CS_POM_VERSION
  cd ../..
  removeFolderWithProtectedFiles contribution
  ;;

alot-of-project1)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: $CS_POM_VERSION
  mkdir -p .ci-temp/
  cd .ci-temp/
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#RxJava/RxJava/' projects-to-test-on.properties
  sed -i.'' 's/#java-design-patterns/java-design-patterns/' projects-to-test-on.properties
  sed -i.'' 's/#MaterialDesignLibrary/MaterialDesignLibrary/' projects-to-test-on.properties
  sed -i.'' 's/#apache-ant/apache-ant/' projects-to-test-on.properties
  sed -i.'' 's/#apache-jsecurity/apache-jsecurity/' projects-to-test-on.properties
  sed -i.'' 's/#android-launcher/android-launcher/' projects-to-test-on.properties
  cd ../../../
  mvn -e clean install -Pno-validations
  cd .ci-temp/contribution/checkstyle-tester
  export MAVEN_OPTS="-Xmx2048m"
  groovy ./launch.groovy --listOfProjects projects-to-test-on.properties \
      --config checks-nonjavadoc-error.xml --checkstyleVersion $CS_POM_VERSION
  cd ../..
  removeFolderWithProtectedFiles contribution
  ;;

*)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac

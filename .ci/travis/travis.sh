#!/bin/bash
# Attention, there is no "-x" to avoid problems on Travis
set -e

case $1 in

nondex)
  # exclude ConfigurationLoaderTest till https://github.com/TestingResearchIllinois/NonDex/issues/112
  mvn --fail-never clean nondex:nondex -Dtest='*,!ConfigurationLoaderTest'
  cat `grep -RlE 'td class=.x' .nondex/ | cat` < /dev/null > output.txt
  RESULT=$(cat output.txt | wc -c)
  cat output.txt
  echo 'Size of output:'$RESULT
  if [[ $RESULT != 0 ]]; then false; fi
  ;;

assembly-run-all-jar)
  mvn clean package -Passembly
  CS_POM_VERSION=$(mvn -q -Dexec.executable='echo' -Dexec.args='${project.version}' --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo version:$CS_POM_VERSION
  java -jar target/checkstyle-$CS_POM_VERSION-all.jar -c /google_checks.xml \
        src/it/resources/com/google/checkstyle/test/chapter3filestructure/rule332nolinewrap/InputNoLineWrapGood.java > output.log
  if grep -vE '(Starting audit)|(warning)|(Audit done.)' output.log ; then exit 1; fi
  if grep 'warning' output.log ; then exit 1; fi
  ;;

sonarqube)
  if [[ $TRAVIS_PULL_REQUEST =~ ^([0-9]*)$ ]]; then exit 0; fi
  mvn clean package cobertura:cobertura sonar:sonar \
       -Dsonar.host.url=https://sonarqube.com \
       -Dsonar.login=$SONAR_TOKEN \
       -Dcobertura.report.format=xml -Dmaven.test.failure.ignore=true \
       -Dcheckstyle.skip=true -Dpmd.skip=true -Dcheckstyle.ant.skip=true
  ;;

release-dry-run)
  mvn release:prepare -DdryRun=true --batch-mode -Darguments='-DskipTests -DskipITs \
    -Dcobertura.skip=true -Dpmd.skip=true -Dfindbugs.skip=true  -Dxml.skip=true \
    -Dcheckstyle.ant.skip=true -Dcheckstyle.skip=true -Dgpg.skip=true'
  ;;

releasenotes-gen)
  .ci/travis/xtr_releasenotes-gen.sh
  ;;

pr-description)
  .ci/travis/xtr_pr-description.sh
  ;;

check-chmod)
  .ci/travis/checkchmod.sh
  ;;

all-sevntu-checks)
  xmlstarlet sel --net --template -m .//module -v "@name" -n config/checkstyle_sevntu_checks.xml \
    | grep -vE "Checker|TreeWalker|Filter|Holder" | grep -v "^$" \
    | sort | uniq | sed "s/Check$//" > file.txt
  wget -q http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/allclasses-frame.html -O - | html2text \
    | grep -E "Check$" | cut -d " " -f6 \
    | sort | uniq | sed "s/Check$//" > web.txt
  diff -u web.txt file.txt
  ;;

no-exception-test-checkstyle-sevntu-checkstyle)
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-for-travis.properties
  sed -i.'' 's/#checkstyle/checkstyle/' projects-for-travis.properties
  sed -i.'' 's/#sevntu-checkstyle/sevntu-checkstyle/' projects-for-travis.properties
  cd ../../
  mvn clean install -Pno-validations
  cd contribution/checkstyle-tester
  groovy ./launch.groovy projects-for-travis.properties checks-nonjavadoc-error.xml
  ;;

no-exception-test-guava)
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-for-travis.properties
  sed -i.'' 's/#guava/guava/' projects-for-travis.properties
  cd ../../
  mvn clean install -Pno-validations
  cd contribution/checkstyle-tester
  groovy ./launch.groovy projects-for-travis.properties checks-nonjavadoc-error.xml
  ;;

no-exception-test-guava-with-google-checks)
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-for-travis.properties
  sed -i.'' 's/#guava/guava/' projects-for-travis.properties
  cd ../../
  mvn clean install -Pno-validations
  sed -i.'' 's/warning/ignore/' src/main/resources/google_checks.xml
  cd contribution/checkstyle-tester
  groovy ./launch.groovy projects-for-travis.properties ../../src/main/resources/google_checks.xml
  ;;

no-exception-test-hibernate)
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-for-travis.properties
  sed -i.'' 's/#hibernate-orm/hibernate-orm/' projects-for-travis.properties
  cd ../../
  mvn clean install -Pno-validations
  cd contribution/checkstyle-tester
  groovy ./launch.groovy projects-for-travis.properties checks-nonjavadoc-error.xml
  ;;

no-exception-test-findbugs)
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-for-travis.properties
  sed -i.'' 's/#findbugs/findbugs/' projects-for-travis.properties
  cd ../../
  mvn clean install -Pno-validations
  cd contribution/checkstyle-tester
  groovy ./launch.groovy projects-for-travis.properties checks-nonjavadoc-error.xml
  ;;

no-exception-test-spring-framework)
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-for-travis.properties
  sed -i.'' 's/#spring-framework/spring-framework/' projects-for-travis.properties
  cd ../../
  mvn clean install -Pno-validations
  cd contribution/checkstyle-tester
  groovy ./launch.groovy projects-for-travis.properties checks-nonjavadoc-error.xml
  ;;

no-exception-test-hbase)
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-for-travis.properties
  sed -i.'' 's/#Hbase/Hbase/' projects-for-travis.properties
  cd ../../
  mvn clean install -Pno-validations
  cd contribution/checkstyle-tester
  groovy ./launch.groovy projects-for-travis.properties checks-nonjavadoc-error.xml
  ;;

no-exception-test-Pmd-elasticsearch-lombok-ast)
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-for-travis.properties
  sed -i.'' 's/#pmd/pmd/' projects-for-travis.properties
  sed -i.'' 's/#elasticsearch/elasticsearch/' projects-for-travis.properties
  sed -i.'' 's/#lombok-ast/lombok-ast/' projects-for-travis.properties
  cd ../../
  mvn clean install -Pno-validations
  cd contribution/checkstyle-tester
  groovy ./launch.groovy projects-for-travis.properties checks-nonjavadoc-error.xml
  ;;

no-exception-test-alot-of-project1)
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-for-travis.properties
  sed -i.'' 's/#RxJava/RxJava/' projects-for-travis.properties
  sed -i.'' 's/#java-design-patterns/java-design-patterns/' projects-for-travis.properties
  sed -i.'' 's/#MaterialDesignLibrary/MaterialDesignLibrary/' projects-for-travis.properties
  sed -i.'' 's/#apache-ant/apache-ant/' projects-for-travis.properties
  sed -i.'' 's/#apache-jsecurity/apache-jsecurity/' projects-for-travis.properties
  sed -i.'' 's/#android-launcher/android-launcher/' projects-for-travis.properties
  cd ../../
  mvn clean install -Pno-validations
  cd contribution/checkstyle-tester
  groovy ./launch.groovy projects-for-travis.properties checks-nonjavadoc-error.xml
  ;;

*)
  echo "Unexpected GOAL mode: $GOAL"
  exit 1
  ;;

esac

#!/bin/bash
# Attention, there is no "-x" to avoid problems on Travis
set -e

case "$GOAL" in

nondex)
  mvn --fail-never clean nondex:nondex
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

all-sevntu-checks)
  xmlstarlet sel --net --template -m .//module -v "@name" -n config/checkstyle_sevntu_checks.xml \
    | grep -vE "Checker|TreeWalker|Filter|Holder" | grep -v "^$" \
    | sort | uniq | sed "s/Check$//" > file.txt
  wget -q http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/allclasses-frame.html -O - | html2text \
    | grep -E "Check$" | cut -d " " -f6 \
    | sort | uniq | sed "s/Check$//" > web.txt
  diff -u web.txt file.txt
  ;;

*)
  echo "Unexpected GOAL mode: $GOAL"
  exit 1
  ;;

esac

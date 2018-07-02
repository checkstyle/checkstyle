#!/bin/bash

#This script is used at distelli-manifest.yml
#Run "firefox target/site/checkstyle.html" after completion to review html report

set -e

uname -a
mvn --version
mvn -B -e clean install -Pno-validations
git clone https://github.com/checkstyle/contribution && cd contribution/checkstyle-tester
sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
sed -i.'' 's/#spring-framework/spring-framework/' projects-to-test-on.properties
sed -i.'' 's/#nbia-dcm4che-tools/nbia-dcm4che-tools/' projects-to-test-on.properties
sed -i.'' 's/#spotbugs/spotbugs/' projects-to-test-on.properties
sed -i.'' 's/#pmd/pmd/' projects-to-test-on.properties
sed -i.'' 's/#apache-ant/apache-ant/' projects-to-test-on.properties
./launch.sh -Dcheckstyle.config.location=checks-only-javadoc-error.xml
if grep "Got an exception" target/site/checkstyle.html; then
  echo "[ERROR] Exceptions detected"
  exit 1
else
  echo "[INFO] Finished without exceptions"
  exit 0
fi


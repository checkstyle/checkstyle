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

case $1 in

sonarqube)
  # token could be generated at https://sonarcloud.io/account/security/
  # executon on local:
  # SONAR_TOKEN=xxxxxx PR=xxxxxx WERCKER_GIT_BRANCH=xxxxxx ./.ci/travis/travis.sh sonarqube
  if [[ $PR && $PR =~ ^([0-9]*)$ ]]; then
      SONAR_PR_VARIABLES="-Dsonar.pullrequest.key=$PR"
      SONAR_PR_VARIABLES+=" -Dsonar.pullrequest.branch=$WERCKER_GIT_BRANCH"
      SONAR_PR_VARIABLES+=" -Dsonar.pullrequest.base=master"
      echo "SONAR_PR_VARIABLES: "$SONAR_PR_VARIABLES
  fi
  if [[ -z $SONAR_TOKEN ]]; then echo "SONAR_TOKEN is not set"; sleep 5s; exit 1; fi
  export MAVEN_OPTS='-Xmx2000m'
  mvn -e -Pno-validations clean package sonar:sonar $SONAR_PR_VARIABLES \
       -Dsonar.host.url=https://sonarcloud.io \
       -Dsonar.login=$SONAR_TOKEN \
       -Dsonar.projectKey=org.checkstyle:checkstyle \
       -Dsonar.organization=checkstyle
  echo "report-task.txt:"
  cat target/sonar/report-task.txt
  echo "Verification of sonar gate status"
  checkout_from https://github.com/viesure/blog-sonar-build-breaker.git
  sed -i'' "s|our.sonar.server|sonarcloud.io|" \
    .ci-temp/blog-sonar-build-breaker/sonar_break_build.sh
  export SONAR_API_TOKEN=$SONAR_TOKEN
  .ci-temp/blog-sonar-build-breaker/sonar_break_build.sh
  ;;


no-error-pgjdbc)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/pgjdbc/pgjdbc.git
  cd .ci-temp/pgjdbc/pgjdbc
  mvn -e checkstyle:check -Dcheckstyle.version=${CS_POM_VERSION}
  cd ../../
  rm -rf pgjdbc
  ;;

no-error-orekit)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/Hipparchus-Math/hipparchus.git
  cd .ci-temp/hipparchus
  # checkout to version that Orekit expects
  SHA_HIPPARCHUS="4c6c6fc45e859e""ae2d4eb091a3a3c0a7a458b8d9"
  git checkout $SHA_HIPPARCHUS
  mvn install -DskipTests
  cd -
  checkout_from https://github.com/CS-SI/Orekit.git
  cd .ci-temp/Orekit
  # no CI is enforced in project, so to make our build stable we should
  # checkout to latest release/development (annotated tag or hash) or sha that have fix we need
  # git checkout $(git describe --abbrev=0 --tags)
  git checkout "a7e67ce73803c67a""ad90e0b28ed77a7781dc28a9"
  mvn -e compile checkstyle:check -Dorekit.checkstyle.version=${CS_POM_VERSION}
  cd ../
  rm -rf Orekit
  ;;

no-error-xwiki)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/xwiki/xwiki-commons.git
  cd .ci-temp/xwiki-commons
  SHA_XWIKI="8a2e04689fb8e707a3457833d""d44c909a""cc43e5b"
  git checkout $SHA_XWIKI
  mvn -f xwiki-commons-tools/xwiki-commons-tool-verification-resources/pom.xml \
    install -DskipTests -Dcheckstyle.version=${CS_POM_VERSION}
  mvn -e test-compile checkstyle:check@default -Dcheckstyle.version=${CS_POM_VERSION}
  cd ../../
  rm -rf xwiki-commons
  ;;

no-error-apex-core)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/apache/incubator-apex-core.git
  cd .ci-temp/incubator-apex-core
  mvn -e compile checkstyle:check -Dcheckstyle.version=${CS_POM_VERSION}
  cd ../
  rm -rf incubator-apex-core
  ;;

no-error-equalsverifier)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/jqno/equalsverifier.git
  cd .ci-temp/equalsverifier
  mvn -e compile checkstyle:check -Dcheckstyle.version=${CS_POM_VERSION}
  cd ../
  rm -rf equalsverifier
  ;;

no-error-hibernate-search)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/hibernate/hibernate-search.git
  cd .ci-temp/hibernate-search
  mvn -e clean install -DskipTests=true -Dtest.elasticsearch.run.skip=true \
     -Dcheckstyle.skip=true -Dforbiddenapis.skip=true \
     -Dpuppycrawl.checkstyle.version=${CS_POM_VERSION}
  mvn -e checkstyle:check  -Dpuppycrawl.checkstyle.version=${CS_POM_VERSION}
  cd ../
  rm -rf hibernate-search
  ;;

no-error-htmlunit)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  echo "checkouting project sources ..."
  svn -q export https://svn.code.sf.net/p/htmlunit/code/trunk/htmlunit@r14923 htmlunit
  cd htmlunit
 sed -i "s/            <version>2.28-SNAPSHOT/            <version>2.28-20171106.080245-12/" pom.xml
  echo "Running checkstyle validation ..."
  mvn -e compile checkstyle:check -Dcheckstyle.version=${CS_POM_VERSION}
  cd ../
  rm -rf htmlunit
  ;;

no-error-checkstyles-sevntu)
  set -e
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  mvn -e compile verify -Dmaven.sevntu-checkstyle-check.checkstyle.version=${CS_POM_VERSION} \
    -Dmaven.test.skip=true -Dcheckstyle.ant.skip=true -Dpmd.skip=true -Dspotbugs.skip=true \
    -Djacoco.skip=true -Dforbiddenapis.skip=true -Dxml.skip=true
  ;;

no-error-sevntu-checks)
  set -e
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/sevntu-checkstyle/sevntu.checkstyle.git
  cd .ci-temp/sevntu.checkstyle/sevntu-checks
  mvn -e -Pno-validations verify  -Dcheckstyle.skip=false -Dcheckstyle.version=${CS_POM_VERSION} \
     -Dcheckstyle.configLocation=../../../config/checkstyle_checks.xml
  cd ../../
  rm -rf sevntu.checkstyle
  ;;

no-error-contribution)
  set -e
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/checkstyle/contribution.git
  cd .ci-temp/contribution
  cd patch-diff-report-tool
  mvn -e verify -DskipTests -Dcheckstyle.version=${CS_POM_VERSION} \
     -Dcheckstyle.configLocation=../../../config/checkstyle_checks.xml
  cd ../
  cd releasenotes-builder
  mvn -e verify -DskipTests -Dcheckstyle.version=${CS_POM_VERSION} \
     -Dcheckstyle.configLocation=../../../config/checkstyle_checks.xml
  cd ../
  cd ../../
  rm -rf checkstyle
  ;;

no-error-methods-distance)
  set -e
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/sevntu-checkstyle/methods-distance.git
  cd .ci-temp/methods-distance
  mvn -e verify -DskipTests -Dcheckstyle-version=${CS_POM_VERSION} \
     -Dcheckstyle.configLocation=../../config/checkstyle_checks.xml
  cd ../../
  rm -rf checkstyle
  ;;

no-error-strata)
  set -e
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/OpenGamma/Strata.git
  cd .ci-temp/Strata
  STRATA_CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${checkstyle.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  mvn install -e -B -Dstrict -DskipTests \
     -Dforbiddenapis.skip=true -Dcheckstyle.version=${CS_POM_VERSION} \
     -Dcheckstyle.config.suffix="-v$STRATA_CS_POM_VERSION"
  cd ../
  rm -rf Strata
  ;;

no-error-spring-integration)
  set -e
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/spring-projects/spring-integration.git
  cd .ci-temp/spring-integration
  PROP_MAVEN_LOCAL="mavenLocal"
  PROP_CS_VERSION="checkstyleVersion"
  ./gradlew clean check --parallel -x test -P$PROP_MAVEN_LOCAL -P$PROP_CS_VERSION=${CS_POM_VERSION}
  cd ../
  rm -rf spring-integration
  ;;

no-exception-struts)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/checkstyle/contribution.git
  cd .ci-temp/contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-for-wercker.properties
  sed -i'' 's/#apache-struts/apache-struts/' projects-for-wercker.properties
  groovy ./launch.groovy --listOfProjects projects-for-wercker.properties \
      --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION}
  cd ../../
  rm -rf contribution
  ;;

no-exception-checkstyle-sevntu)
  set -e
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/checkstyle/contribution.git
  cd .ci-temp/contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-for-wercker.properties
  sed -i'' 's/#local-checkstyle/local-checkstyle/' projects-for-wercker.properties
  sed -i'' 's/#sevntu-checkstyle/sevntu-checkstyle/' projects-for-wercker.properties
  groovy ./launch.groovy --listOfProjects projects-for-wercker.properties \
      --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION}
  cd ../../
  rm -rf contribution
  ;;

no-exception-checkstyle-sevntu-javadoc)
  set -e
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/checkstyle/contribution.git
  cd .ci-temp/contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-for-wercker.properties
  sed -i'' 's/#local-checkstyle/local-checkstyle/' projects-for-wercker.properties
  sed -i'' 's/#sevntu-checkstyle/sevntu-checkstyle/' projects-for-wercker.properties
  groovy ./launch.groovy --listOfProjects projects-for-wercker.properties \
      --config checks-only-javadoc-error.xml --checkstyleVersion ${CS_POM_VERSION}
  cd ../../
  rm -rf contribution
  ;;

no-exception-guava)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/checkstyle/contribution.git
  cd .ci-temp/contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-for-wercker.properties
  sed -i'' 's/#guava/guava/' projects-for-wercker.properties
  groovy ./launch.groovy --listOfProjects projects-for-wercker.properties \
      --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION}
  cd ../../
  rm -rf contribution
  ;;

no-exception-hibernate-orm)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/checkstyle/contribution.git
  cd .ci-temp/contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#hibernate-orm/hibernate-orm/' projects-to-test-on.properties
  groovy ./launch.groovy --listOfProjects projects-for-wercker.properties \
      --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION}
  cd ../../
  rm -rf contribution
  ;;

no-exception-spotbugs)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/checkstyle/contribution.git
  cd .ci-temp/contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#spotbugs/spotbugs/' projects-to-test-on.properties
  groovy ./launch.groovy --listOfProjects projects-to-test-on.properties \
      --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION}
  cd ../../
  rm -rf contribution
  ;;

no-exception-spring-framework)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/checkstyle/contribution.git
  cd .ci-temp/contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#spring-framework/spring-framework/' projects-to-test-on.properties
  groovy ./launch.groovy --listOfProjects projects-to-test-on.properties \
      --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION}
  cd ../../
  rm -rf contribution
  ;;

no-exception-hbase)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/checkstyle/contribution.git
  cd .ci-temp/contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#Hbase/Hbase/' projects-to-test-on.properties
  groovy ./launch.groovy --listOfProjects projects-to-test-on.properties \
      --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION}
  cd ../../
  rm -rf contribution
  ;;

no-exception-Pmd-elasticsearch-lombok-ast)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/checkstyle/contribution.git
  cd .ci-temp/contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#pmd/pmd/' projects-to-test-on.properties
  sed -i.'' 's/#elasticsearch/elasticsearch/' projects-to-test-on.properties
  sed -i.'' 's/#lombok-ast/lombok-ast/' projects-to-test-on.properties
  groovy ./launch.groovy --listOfProjects projects-to-test-on.properties \
      --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION}
  cd ../../
  rm -rf contribution
  ;;

no-exception-alot-of-projects)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/checkstyle/contribution.git
  cd .ci-temp/contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#RxJava/RxJava/' projects-to-test-on.properties
  sed -i.'' 's/#java-design-patterns/java-design-patterns/' projects-to-test-on.properties
  sed -i.'' 's/#MaterialDesignLibrary/MaterialDesignLibrary/' projects-to-test-on.properties
  sed -i.'' 's/#apache-ant/apache-ant/' projects-to-test-on.properties
  sed -i.'' 's/#apache-jsecurity/apache-jsecurity/' projects-to-test-on.properties
  sed -i.'' 's/#android-launcher/android-launcher/' projects-to-test-on.properties
  groovy ./launch.groovy --listOfProjects projects-to-test-on.properties \
      --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION}
  cd ../../
  rm -rf contribution
  ;;

no-warning-imports-guava)
  PROJECTS=checks-import-order/projects-to-test-imports-guava.properties
  CONFIG=checks-import-order/checks-imports-error-guava.xml
  REPORT=reports/guava/site/index.html
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/checkstyle/contribution.git
  cd .ci-temp/contribution/checkstyle-tester
  groovy ./launch.groovy --listOfProjects $PROJECTS --config $CONFIG \
      --checkstyleVersion ${CS_POM_VERSION}
  RESULT=`grep -A 5 "&#160;Warning</td>" $REPORT | cat`
  cd ../../
  rm -rf contribution
  if [ -z "$RESULT" ]; then
    echo "Inspection did not find any warnings"
  else
    echo "$RESULT"
    echo "Some warnings have been found. Verification failed."
    sleep 5s
    exit 1
  fi
  ;;

no-warning-imports-java-design-patterns)
  PROJECTS=checks-import-order/projects-to-test-imports-java-design-patterns.properties
  CONFIG=checks-import-order/checks-imports-error-java-design-patterns.xml
  REPORT=reports/java-design-patterns/site/index.html
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/checkstyle/contribution.git
  cd .ci-temp/contribution/checkstyle-tester
  groovy ./launch.groovy --listOfProjects $PROJECTS --config $CONFIG \
      --checkstyleVersion ${CS_POM_VERSION}
  RESULT=`grep -A 5 "&#160;Warning</td>" $REPORT | cat`
  cd ../../
  rm -rf contribution
  if [ -z "$RESULT" ]; then
    echo "Inspection did not find any warnings"
  else
    echo "$RESULT"
    echo "Some warnings have been found. Verification failed."
    sleep 5s
    exit 1
  fi
  ;;

*)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac

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

removeFolderWithProtectedFiles() {
  find $1 -delete
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
  removeFolderWithProtectedFiles .ci-temp/blog-sonar-build-breaker
  ;;


no-error-pgjdbc)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/pgjdbc/pgjdbc.git
  cd .ci-temp/pgjdbc
  ./gradlew --no-parallel --no-daemon checkstyleAll \
            -PenableMavenLocal -Pcheckstyle.version=${CS_POM_VERSION}
  cd ../
 removeFolderWithProtectedFiles pgjdbc
  ;;

no-error-orekit)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/Hipparchus-Math/hipparchus.git
  cd .ci-temp/hipparchus
  # checkout to version that Orekit expects
  SHA_HIPPARCHUS="1fb""fb8a2a259a9""7a23e2a387e8fd""c5e0a8402e77"
  git checkout $SHA_HIPPARCHUS
  mvn install -DskipTests
  cd -
  checkout_from https://github.com/CS-SI/Orekit.git
  cd .ci-temp/Orekit
  # no CI is enforced in project, so to make our build stable we should
  # checkout to latest release/development (annotated tag or hash) or sha that have fix we need
  # git checkout $(git describe --abbrev=0 --tags)
  git checkout "b67b419db7014f4b""ad921a1ba""c6c848384ad2b92"
  mvn -e compile checkstyle:check -Dorekit.checkstyle.version=${CS_POM_VERSION}
  cd ..
  removeFolderWithProtectedFiles Orekit
  removeFolderWithProtectedFiles hipparchus
  ;;

no-error-xwiki)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/xwiki/xwiki-commons.git
  cd .ci-temp/xwiki-commons
  SHA_XWIKI="4fa""a8a49de3a70b""ba9c6c3849784e5d""fb642fa8d"
  git checkout $SHA_XWIKI
  mvn -f xwiki-commons-tools/xwiki-commons-tool-verification-resources/pom.xml \
    install -DskipTests -Dcheckstyle.version=${CS_POM_VERSION}
  mvn -e test-compile checkstyle:check@default -Dcheckstyle.version=${CS_POM_VERSION}
  cd ..
  removeFolderWithProtectedFiles xwiki-commons
  ;;

no-error-apex-core)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/checkstyle/apex-core
  cd .ci-temp/apex-core
  mvn -e compile checkstyle:check -Dcheckstyle.version=${CS_POM_VERSION}
  cd ../
  removeFolderWithProtectedFiles apex-core
  ;;

no-error-equalsverifier)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/jqno/equalsverifier.git
  cd .ci-temp/equalsverifier
  mvn -e compile checkstyle:check -Dcheckstyle.version=${CS_POM_VERSION}
  cd ../
  removeFolderWithProtectedFiles equalsverifier
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
  removeFolderWithProtectedFiles hibernate-search
  ;;

no-error-htmlunit)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/HtmlUnit/htmlunit
  cd .ci-temp/htmlunit
  mvn -e compile checkstyle:check -Dcheckstyle.version=${CS_POM_VERSION}
  cd ../
  removeFolderWithProtectedFiles htmlunit
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
  removeFolderWithProtectedFiles sevntu.checkstyle
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
  cd ../../
  removeFolderWithProtectedFiles contribution
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
  cd ..
  removeFolderWithProtectedFiles  methods-distance
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
  removeFolderWithProtectedFiles Strata
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
  removeFolderWithProtectedFiles spring-integration
  ;;

no-error-spring-cloud-gcp)
  set -e
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/spring-cloud/spring-cloud-gcp/blob/master/pom.xml
  cd .ci-temp/spring-cloud-gcp
  mvn -e compile checkstyle:check -Dproject.version=${CS_POM_VERSION} \
     -Dcheckstyle.configLocation=../../config/checkstyle_checks.xml
  cd ..
  removeFolderWithProtectedFiles spring-cloud-gcp
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
  removeFolderWithProtectedFiles contribution
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
  removeFolderWithProtectedFiles contribution
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
  removeFolderWithProtectedFiles contribution
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
  removeFolderWithProtectedFiles contribution
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
  removeFolderWithProtectedFiles contribution
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
  removeFolderWithProtectedFiles contribution
  ;;

no-exception-spoon)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/checkstyle/contribution.git
  cd .ci-temp/contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#spoon/spoon/' projects-to-test-on.properties
  groovy ./launch.groovy --listOfProjects projects-for-wercker.properties \
      --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION}
  cd ../../
  removeFolderWithProtectedFiles contribution
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
  removeFolderWithProtectedFiles contribution
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
  removeFolderWithProtectedFiles contribution
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
  removeFolderWithProtectedFiles contribution
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
  removeFolderWithProtectedFiles contribution
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
  removeFolderWithProtectedFiles contribution
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
  removeFolderWithProtectedFiles contribution
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

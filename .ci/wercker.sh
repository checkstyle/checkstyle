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
  checkout_from https://github.com/CS-SI/Orekit.git
  cd .ci-temp/Orekit
  # no CI is enforced in project, so to make our build stable we should
  # checkout to latest release (annotated tag)
  #git checkout $(git describe --abbrev=0 --tags)
  # Orekit use 'develop' branch as target for PullRequest merges, where all our breaking changes
  # of 8.2 and above are applied
  #git checkout develop
  # due to temporal compilation problems(20180522) we use latest commit where compilation pass
  git checkout 9862be9
  mvn -e compile checkstyle:check -Dorekit.checkstyle.version=${CS_POM_VERSION}
  cd ../
  rm -rf Orekit
  ;;

no-error-xwiki)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  # till https://github.com/xwiki/xwiki-commons/pull/39
  checkout_from https://github.com/checkstyle/xwiki-commons.git
  cd .ci-temp/xwiki-commons
  # till https://github.com/xwiki/xwiki-commons/pull/39
  git checkout i5812-rename-util
  mvn -f xwiki-commons-tools/xwiki-commons-tool-verification-resources/pom.xml \
    install -DskipTests -Dcheckstyle.version=${CS_POM_VERSION}
  mvn -e test-compile checkstyle:check -Dcheckstyle.version=${CS_POM_VERSION}
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

no-error-hibernate-search)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/hibernate/hibernate-search.git
  cd .ci-temp/hibernate-search
  mvn -e clean install -DskipTests=true -Dtest.elasticsearch.host.provided=true \
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
     -Dcheckstyle.configLocation=../../config/checkstyle_checks.xml
  cd ../../
  rm -rf sevntu.checkstyle
  ;;

no-error-strata)
  set -e
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  checkout_from https://github.com/OpenGamma/Strata.git
  cd .ci-temp/Strata
  mvn install -e -B -Dstrict -DskipTests \
     -Dforbiddenapis.skip=true -Dcheckstyle.version=${CS_POM_VERSION}
  cd ../
  rm -rf Strata
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
  sed -i'' 's/#checkstyle/checkstyle/' projects-for-wercker.properties
  sed -i'' 's/#sevntu-checkstyle/sevntu-checkstyle/' projects-for-wercker.properties
  groovy ./launch.groovy --listOfProjects projects-for-wercker.properties \
      --config checks-nonjavadoc-error.xml --checkstyleVersion ${CS_POM_VERSION}
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

*)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac

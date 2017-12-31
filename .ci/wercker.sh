#!/bin/bash
# Attention, there is no "-x" to avoid problems on Wercker
set -e

case $1 in

no-error-pgjdbc)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  for i in 1 2 3 4 5; do git clone https://github.com/pgjdbc/pgjdbc.git && break || sleep 15; done
  cd pgjdbc/pgjdbc
  mvn -e checkstyle:check -Dcheckstyle.version=${CS_POM_VERSION}
  cd ../../
  rm -rf pgjdbc
  ;;

no-error-orekit)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  for i in 1 2 3 4 5; do git clone https://github.com/Hipparchus-Math/hipparchus.git && break || sleep 15; done
  cd hipparchus
  git checkout 905006092493e350dcd68dd7b2ec1dedaf4983b7
  mvn -e clean install -DskipTests
  cd ../
  for i in 1 2 3 4 5; do git clone https://github.com/CS-SI/Orekit.git && break || sleep 15; done
  cd Orekit
  # Orekit use 'develop' branch as target for PullRequest merges
  git checkout develop
  mvn -e compile checkstyle:check -Dorekit.checkstyle.version=${CS_POM_VERSION}
  cd ../
  rm -rf hipparchus Orekit
  ;;

no-error-xwiki)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  for i in 1 2 3 4 5; do git clone https://github.com/xwiki/xwiki-commons/ && break || sleep 15; done
  cd xwiki-commons
  git checkout 44b0c0048c516dae20cf5f8a71181af836549484
  mvn -e install -DskipTests -Dxwiki.clirr.skip=true checkstyle:check -Dcheckstyle.version=${CS_POM_VERSION}
  cd ../../
  rm -rf xwiki-commons
  ;;

no-error-apex-core)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  for i in 1 2 3 4 5; do git clone https://github.com/apache/incubator-apex-core/ && break || sleep 15; done
  cd incubator-apex-core
  mvn -e compile checkstyle:check -Dcheckstyle.version=${CS_POM_VERSION}
  cd ../
  rm -rf incubator-apex-core
  ;;

no-error-hibernate-search)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  for i in 1 2 3 4 5; do git clone https://github.com/hibernate/hibernate-search.git && break || sleep 15; done
  cd hibernate-search
  mvn -e -s settings-example.xml clean install -DskipTests=true -Dtest.elasticsearch.host.provided=true -Dpuppycrawl.checkstyle.version=${CS_POM_VERSION}
  mvn -e -s settings-example.xml checkstyle:check  -Dpuppycrawl.checkstyle.version=${CS_POM_VERSION}
  cd ../
  rm -rf hibernate-search
  ;;

no-error-htmlunit)
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
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
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  mvn -e compile verify -Dmaven.sevntu-checkstyle-check.checkstyle.version=${CS_POM_VERSION} -Dmaven.test.skip=true -Dcheckstyle.ant.skip=true -Dpmd.skip=true -Dfindbugs.skip=true -Dcobertura.skip=true -Dforbiddenapis.skip=true -Dxml.skip=true
  ;;

no-error-sevntu-checks)
  set -e
  CS_POM_VERSION=$(mvn -e -q -Dexec.executable='echo' -Dexec.args='${project.version}' --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  echo CS_version: ${CS_POM_VERSION}
  for i in 1 2 3 4 5; do git clone https://github.com/sevntu-checkstyle/sevntu.checkstyle && break || sleep 15; done
  cd sevntu.checkstyle/sevntu-checks
  mvn -e -Pno-validations verify  -Dcheckstyle.skip=false -Dcheckstyle.version=${CS_POM_VERSION} -Dcheckstyle.configLocation=../../config/checkstyle_checks.xml
  ;;

no-exception-struts)
  for i in 1 2 3 4 5; do git clone https://github.com/checkstyle/contribution && break || sleep 15; done
  cd contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-for-wercker.properties
  sed -i'' 's/#apache-struts/apache-struts/' projects-for-wercker.properties
  groovy ./launch.groovy --listOfProjects projects-for-wercker.properties --config checks-nonjavadoc-error.xml
  cd ../../
  rm -rf contribution
  ;;

no-exception-checkstyle-sevntu)
  set -e
  for i in 1 2 3 4 5; do git clone https://github.com/checkstyle/contribution && break || sleep 15; done
  cd contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-for-wercker.properties
  sed -i'' 's/#checkstyle/checkstyle/' projects-for-wercker.properties
  sed -i'' 's/#sevntu-checkstyle/sevntu-checkstyle/' projects-for-wercker.properties
  groovy ./launch.groovy --listOfProjects projects-for-wercker.properties --config checks-nonjavadoc-error.xml
  cd ../../
  rm -rf contribution
  ;;

no-exception-guava)
  for i in 1 2 3 4 5; do git clone https://github.com/checkstyle/contribution && break || sleep 15; done
  cd contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-for-wercker.properties
  sed -i'' 's/#guava/guava/' projects-for-wercker.properties
  groovy ./launch.groovy --listOfProjects projects-for-wercker.properties --config checks-nonjavadoc-error.xml
  cd ../../
  rm -rf contribution
  ;;

no-exception-hibernate-orm)
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#hibernate-orm/hibernate-orm/' projects-to-test-on.properties
  groovy ./launch.groovy --listOfProjects projects-for-wercker.properties --config checks-nonjavadoc-error.xml
  cd ../../
  rm -rf contribution
  ;;

no-exception-findbugs)
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#findbugs/findbugs/' projects-to-test-on.properties
  groovy ./launch.groovy --listOfProjects projects-to-test-on.properties --config checks-nonjavadoc-error.xml
  cd ../../
  rm -rf contribution
  ;;

no-exception-spring-framework)
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#spring-framework/spring-framework/' projects-to-test-on.properties
  groovy ./launch.groovy --listOfProjects projects-to-test-on.properties --config checks-nonjavadoc-error.xml
  cd ../../
  rm -rf contribution
  ;;

no-exception-hbase)
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#Hbase/Hbase/' projects-to-test-on.properties
  groovy ./launch.groovy --listOfProjects projects-to-test-on.properties --config checks-nonjavadoc-error.xml
  cd ../../
  rm -rf contribution
  ;;

no-exception-Pmd-elasticsearch-lombok-ast)
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#pmd/pmd/' projects-to-test-on.properties
  sed -i.'' 's/#elasticsearch/elasticsearch/' projects-to-test-on.properties
  sed -i.'' 's/#lombok-ast/lombok-ast/' projects-to-test-on.properties
  groovy ./launch.groovy --listOfProjects projects-to-test-on.properties --config checks-nonjavadoc-error.xml
  cd ../../
  rm -rf contribution
  ;;

no-exception-alot-of-projects)
  git clone https://github.com/checkstyle/contribution
  cd contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#RxJava/RxJava/' projects-to-test-on.properties
  sed -i.'' 's/#java-design-patterns/java-design-patterns/' projects-to-test-on.properties
  sed -i.'' 's/#MaterialDesignLibrary/MaterialDesignLibrary/' projects-to-test-on.properties
  sed -i.'' 's/#apache-ant/apache-ant/' projects-to-test-on.properties
  sed -i.'' 's/#apache-jsecurity/apache-jsecurity/' projects-to-test-on.properties
  sed -i.'' 's/#android-launcher/android-launcher/' projects-to-test-on.properties
  groovy ./launch.groovy --listOfProjects projects-to-test-on.properties --config checks-nonjavadoc-error.xml
  cd ../../
  rm -rf contribution
  ;;

*)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac

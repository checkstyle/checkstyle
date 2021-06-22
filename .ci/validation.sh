#!/bin/bash
set -e

source ./.ci/util.sh

addCheckstyleBundleToAntResolvers() {
  xmlstarlet ed --inplace \
    -s '/ivysettings/resolvers' -t elem -n filesystem \
    -i '/ivysettings/resolvers/filesystem[last()]' -t attr -n name -v local-checkstyle \
    -s '/ivysettings/resolvers/filesystem[last()]' -t elem -n artifact \
    -i '/ivysettings/resolvers/filesystem[last()]/artifact' -t attr -n pattern -v \
    '${base.dir}/../../target/[artifact]-[revision]-all.[ext]' \
    -s '/ivysettings/modules' -t elem -n module \
    -i '/ivysettings/modules/module[last()]' -t attr -n organisation -v com.puppycrawl.tools \
    -i '/ivysettings/modules/module[last()]' -t attr -n name -v checkstyle \
    -i '/ivysettings/modules/module[last()]' -t attr -n resolver -v local-checkstyle \
    ivysettings.xml
}

case $1 in

all-sevntu-checks)
  working_dir=.ci-temp/all-sevntu-checks
  mkdir -p $working_dir
  xmlstarlet sel --net --template -m .//module -v "@name" -n config/checkstyle_sevntu_checks.xml \
    | grep -vE "Checker|TreeWalker|Filter|Holder" | grep -v "^$" \
    | sed "s/com\.github\.sevntu\.checkstyle\.checks\..*\.//" \
    | sort | uniq | sed "s/Check$//" > $working_dir/file.txt
  wget -q http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/allclasses-frame.html -O - \
    | grep "<li>" | cut -d '>' -f 3 | sed "s/<\/a//" \
    | grep -E "Check$" \
    | sort | uniq | sed "s/Check$//" > $working_dir/web.txt
  # temporal ignore list
  # sed -i.backup '/Jsr305Annotations/d' web.txt
  diff -u $working_dir/web.txt $working_dir/file.txt
  removeFolderWithProtectedFiles $working_dir
  ;;

check-missing-pitests)
  fail=0
  mkdir -p target

  list=($(cat pom.xml | \
    xmlstarlet sel --ps -N pom="http://maven.apache.org/POM/4.0.0" \
    -t -v '//pom:profile[./pom:id[contains(text(),'pitest')]]//pom:targetClasses/pom:param'))

  #  Temporary skip for Metadata generator related files for
  #  https://github.com/checkstyle/checkstyle/issues/8761
  list=("com.puppycrawl.tools.checkstyle.meta.*" "${list[@]}")

  CMD="find src/main/java -type f ! -name 'package-info.java'"

  for item in "${list[@]}"
  do
    item=${item//\./\/}
    if [[ $item == */\*  ]] ; then
     item=$item
    else
      if [[ $item != *\* ]] ; then
        item="$item.java"
      else
        item="${item::-1}.java"
      fi
    fi

    CMD="$CMD -and ! -wholename '*/$item'"
  done

  CMD="$CMD | sort > target/result.txt"
  eval $CMD

  results=$(cat target/result.txt)

  echo "List of missing files in pitest profiles: $results"

  if [[ -n $results ]] ; then
    fail=1
  fi

  sleep 5s
  exit $fail
  ;;

eclipse-static-analysis)
  mvn -e --no-transfer-progress clean compile exec:exec -Peclipse-compiler
  ;;

eclipse-static-analysis-java11)
  # Ensure that project sources can be compiled by eclipse with Java11 language features.
  mvn -e --no-transfer-progress clean compile exec:exec -Peclipse-compiler -D java.version=11
  ;;

java11-verify)
  # Ensure that project sources can be compiled by jdk with Java11 language features.
  mvn -e --no-transfer-progress clean verify -D java.version=11
  ;;

nondex)
  # Below we exclude test that fails due to picocli library usage
  mvn -e --no-transfer-progress --fail-never clean nondex:nondex -DargLine='-Xms1024m -Xmx2048m' \
    -Dtest=!JavadocPropertiesGeneratorTest#testNonExistentArgument
  mkdir -p .ci-temp
  cat `grep -RlE 'td class=.x' .nondex/ | cat` < /dev/null > .ci-temp/output.txt
  RESULT=$(cat .ci-temp/output.txt | wc -c)
  cat .ci-temp/output.txt
  echo 'Size of output:'$RESULT
  if [[ $RESULT != 0 ]]; then sleep 5s; false; fi
  rm .ci-temp/output.txt
  ;;

no-error-pmd)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo "CS_version: ${CS_POM_VERSION}"
  checkout_from "-b checkstyle-7417 https://github.com/checkstyle/build-tools.git"
  cd .ci-temp/build-tools/
  PMD_POM_VERSION=$(mvn -e --no-transfer-progress -q -Dexec.executable='echo' \
    -Dexec.args='${project.version}' \
     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  mvn -e --no-transfer-progress install
  cd ..
  git clone https://github.com/pmd/pmd.git
  cd pmd
  # Using specific commit so that build-tools dependencies match
  git checkout 342dc1d03aaa1082e42f7496d6869d15859af321
  mvn -e --no-transfer-progress install checkstyle:check -Dcheckstyle.version=${CS_POM_VERSION} \
    -Dpmd.build-tools.version=${PMD_POM_VERSION}
  cd ..
  removeFolderWithProtectedFiles build-tools
  removeFolderWithProtectedFiles pmd
  ;;

no-violation-test-configurate)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo "CS_version: ${CS_POM_VERSION}"
  mkdir -p .ci-temp
  cd .ci-temp
  git clone https://github.com/SpongePowered/Configurate.git
  cd Configurate
  ./gradlew -PcheckstyleVersion="${CS_POM_VERSION}" -x test check
  cd ..
  removeFolderWithProtectedFiles Configurate
  ;;

no-violation-test-josm)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo "CS_version: ${CS_POM_VERSION}"
  mkdir -p .ci-temp
  cd .ci-temp
  TESTED=$(wget -q -O - https://josm.openstreetmap.de/wiki/TestedVersion?format=txt)
  echo "JOSM revision: ${TESTED}"
  svn -q --force export https://josm.openstreetmap.de/svn/trunk/ -r "${TESTED}" --native-eol LF josm
  cd josm
  sed -i -E "s/(name=\"checkstyle\" rev=\")([0-9]+\.[0-9]+(-SNAPSHOT)?)/\1${CS_POM_VERSION}/" \
   tools/ivy.xml
  addCheckstyleBundleToAntResolvers
  ant -v checkstyle
  grep "<error" checkstyle-josm.xml | cat > errors.log
  echo "Checkstyle Errors:"
  RESULT=$(wc -l < errors.log)
  cat errors.log
  echo "Size of output: ${RESULT}"
  cd ..
  removeFolderWithProtectedFiles josm
  if [[ ${RESULT} != 0 ]]; then false; fi
  ;;

no-error-xwiki)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo version:$CS_POM_VERSION
  mvn -e --no-transfer-progress clean install -Pno-validations
  checkout_from "https://github.com/xwiki/xwiki-commons.git"
  cd .ci-temp/xwiki-commons
  # Build custom Checkstyle rules
  mvn -e --no-transfer-progress -f \
    xwiki-commons-tools/xwiki-commons-tool-verification-resources/pom.xml \
    install -DskipTests -Dcheckstyle.version=${CS_POM_VERSION}
  # Validate xwiki-commons
  mvn -e --no-transfer-progress checkstyle:check@default -Dcheckstyle.version=${CS_POM_VERSION}
  # Install various required poms and extensions
  mvn -e --no-transfer-progress install:install-file -Dfile=pom.xml -DpomFile=pom.xml
  mvn -e --no-transfer-progress install:install-file -Dfile=xwiki-commons-tools/pom.xml \
    -DpomFile=xwiki-commons-tools/pom.xml
  mvn -e --no-transfer-progress install:install-file \
    -Dfile=xwiki-commons-tools/xwiki-commons-tool-pom/pom.xml \
    -DpomFile=xwiki-commons-tools/xwiki-commons-tool-pom/pom.xml
  mvn -e --no-transfer-progress install:install-file -Dfile=xwiki-commons-pom/pom.xml \
    -DpomFile=xwiki-commons-pom/pom.xml
  mvn -e --no-transfer-progress -f xwiki-commons-tools/xwiki-commons-tool-webjar-handlers/pom.xml \
    install -Dmaven.test.skip -Dcheckstyle.version=${CS_POM_VERSION}
  mvn -e --no-transfer-progress -f xwiki-commons-tools/xwiki-commons-tool-xar/pom.xml \
    install -Dmaven.test.skip -Dcheckstyle.version=${CS_POM_VERSION}
  cd ..
  removeFolderWithProtectedFiles xwiki-commons
  cd ..
  checkout_from https://github.com/xwiki/xwiki-rendering.git
  cd .ci-temp/xwiki-rendering
  # Validate xwiki-rendering
  mvn -e --no-transfer-progress checkstyle:check@default -Dcheckstyle.version=${CS_POM_VERSION}
  cd ..
  removeFolderWithProtectedFiles xwiki-rendering
  cd ..
  checkout_from https://github.com/xwiki/xwiki-platform.git
  cd .ci-temp/xwiki-platform
  git checkout "7904c0f72ff7c9e0f52cd8bd1ee""d5221239fc19c"
  # Validate xwiki-platform
  mvn -e --no-transfer-progress checkstyle:check@default -Dcheckstyle.version=${CS_POM_VERSION}
  cd ..
  removeFolderWithProtectedFiles xwiki-platform
  ;;

verify-no-exception-configs)
  mkdir -p .ci-temp/verify-no-exception-configs
  working_dir=.ci-temp/verify-no-exception-configs
  wget -q \
    --directory-prefix $working_dir \
    --no-clobber \
    https://raw.githubusercontent.com/checkstyle/contribution/master/checkstyle-tester/checks-nonjavadoc-error.xml
  wget -q \
    --directory-prefix $working_dir \
    --no-clobber \
    https://raw.githubusercontent.com/checkstyle/contribution/master/checkstyle-tester/checks-only-javadoc-error.xml
  MODULES_WITH_EXTERNAL_FILES="Filter|ImportControl"
  xmlstarlet sel --net --template -m .//module -v "@name" \
    -n $working_dir/checks-nonjavadoc-error.xml -n $working_dir/checks-only-javadoc-error.xml \
    | grep -vE $MODULES_WITH_EXTERNAL_FILES | grep -v "^$" \
    | sort | uniq | sed "s/Check$//" > $working_dir/web.txt
  xmlstarlet sel --net --template -m .//module -v "@name" -n config/checkstyle_checks.xml \
    | grep -vE $MODULES_WITH_EXTERNAL_FILES | grep -v "^$" \
    | sort | uniq | sed "s/Check$//" > $working_dir/file.txt
  DIFF_TEXT=$(diff -u $working_dir/web.txt $working_dir/file.txt | cat)
  fail=0
  if [[ $DIFF_TEXT != "" ]]; then
    echo "Diff is detected."
    if [[ $PULL_REQUEST =~ ^([0-9]+)$ ]]; then
      LINK_PR=https://api.github.com/repos/checkstyle/checkstyle/pulls/$PULL_REQUEST
      REGEXP="https://github.com/checkstyle/contribution/pull/"
      PR_DESC=$(curl -s -H "Authorization: token $READ_ONLY_TOKEN" $LINK_PR \
                  | jq '.body' | grep $REGEXP | cat )
      echo 'PR Description grepped:'${PR_DESC:0:180}
      if [[ -z $PR_DESC ]]; then
        echo 'You introduce new Check'
        diff -u $working_dir/web.txt $working_dir/file.txt | cat
        echo 'Please create PR to repository https://github.com/checkstyle/contribution'
        echo 'and add your new Check '
        echo '   to file checkstyle-tester/checks-nonjavadoc-error.xml'
        echo 'or to file checkstyle-tester/checks-only-javadoc-error.xml'
        echo 'Place the contribution repository PR link in the description of this PR.'
        echo 'PR for contribution repository will be merged right after this PR.'
        fail=1;
      fi
    else
      diff -u $working_dir/web.txt $working_dir/file.txt | cat
      echo 'file config/checkstyle_checks.xml contains Check that is not present at:'
      echo 'https://github.com/checkstyle/contribution/blob/master/checkstyle-tester/checks-nonjavadoc-error.xml'
      echo 'https://github.com/checkstyle/contribution/blob/master/checkstyle-tester/checks-only-javadoc-error.xml'
      echo 'Please add new Check to one of such files to let Check participate in auto testing'
      fail=1;
    fi
  fi
  removeFolderWithProtectedFiles .ci-temp/verify-no-exception-configs
  sleep 5
  exit $fail
  ;;

verify-regexp-id)
  fail=0
  for FILE in config/*_checks.xml
  do
    a=$(grep -c "<module name=\"Regexp.*" $FILE) || a=0
    b=$(grep "<module name=\"Regexp" -A 1 $FILE | grep -c "<property name=\"id\"") || b=0
    if [ ${a} != ${b} ]
    then
      echo "Error: $FILE has Regexp modules without id property immediately after module name."
      fail=1
    fi
  done
  cd ..
  exit $fail
  ;;

*)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac

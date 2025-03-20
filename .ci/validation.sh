#!/bin/bash
set -e

source ./.ci/util.sh

addCheckstyleBundleToAntResolvers() {
  # shellcheck disable=2016 # we do not want to expand properties in this command
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

function list_tasks() {
  cat "${0}" | sed -E -n 's/^([a-zA-Z0-9\-]*)\)$/\1/p' | sort
}

case $1 in

all-sevntu-checks)
  working_dir=.ci-temp/all-sevntu-checks
  mkdir -p $working_dir
  xmlstarlet sel --net --template -m .//module -v "@name" -n config/checkstyle-sevntu-checks.xml \
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
    -t -v "//pom:profile[./pom:id[contains(text(),'pitest')]]//pom:targetClasses/pom:param"))

  #  Temporary skip for Metadata generator related files for
  #  https://github.com/checkstyle/checkstyle/issues/8761
  #  Coverage for site package is skipped
  #  until https://github.com/checkstyle/checkstyle/issues/13393
  list=("com.puppycrawl.tools.checkstyle.meta.*"
    "com.puppycrawl.tools.checkstyle.site.*" "${list[@]}")

  CMD="find src/main/java -type f ! -name 'package-info.java'"

  for item in "${list[@]}"
  do
    item=${item//\./\/}
    if [[ $item != */\*  ]] ; then
      if [[ $item != *\* ]] ; then
        item="$item.java"
      else
        item="${item::-1}.java"
      fi
    fi

    CMD="$CMD -and ! -wholename '*/$item'"
  done

  CMD="$CMD | sort > target/result.txt"
  eval "$CMD"

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

nondex)
  # Exclude test that fails due to picocli library usage
  SKIPPED_TESTS='!JavadocPropertiesGeneratorTest#testNonExistentArgument,'
  # Exclude test that fails due to stackoverflow error
  SKIPPED_TESTS+='!SingleSpaceSeparatorCheckTest#testNoStackoverflowError'
  mvn -e --no-transfer-progress \
    --fail-never clean nondex:nondex -DargLine='-Xms1024m -Xmx2048m' \
    -Dtest="$SKIPPED_TESTS"

  mkdir -p .ci-temp
  grep -RlE 'td class=.x' .nondex/ | cat > .ci-temp/output.txt
  RESULT=$(cat .ci-temp/output.txt | wc -c)
  cat .ci-temp/output.txt
  echo 'Size of output:'"$RESULT"
  if [[ $RESULT != 0 ]]; then false; fi
  rm .ci-temp/output.txt
  ;;

pr-age)
  # Travis merges the PR commit into origin/master
  # This command undoes that to work with the original branch
  # if it notices a merge commit
  if git show --summary HEAD | grep ^Merge: ;
  then
    git reset --hard "$(git log -n 1 --no-merges --pretty=format:"%h")"
  fi

  PR_MASTER=$(git merge-base origin/master HEAD)
  COMMITS_SINCE_MASTER=$(git rev-list --count "$PR_MASTER"..origin/master)
  MAX_ALLOWED=10

  echo "The PR is based on a master that is $COMMITS_SINCE_MASTER commit(s) old."
  echo "The max allowed is $MAX_ALLOWED."

  if (( $COMMITS_SINCE_MASTER > $MAX_ALLOWED ));
  then
    echo "This PR is too old and should be rebased on origin/master."
    false
  fi
  ;;

test)
  mvn -e --no-transfer-progress clean integration-test failsafe:verify \
  -DargLine='-Xms1024m -Xmx2048m'
  ;;

test-de)
  mvn -e --no-transfer-progress clean integration-test failsafe:verify \
    -Dsurefire.options='-Duser.language=de -Duser.country=DE -Xms1024m -Xmx2048m'
  ;;

test-es)
  mvn -e --no-transfer-progress clean integration-test failsafe:verify \
    -Dsurefire.options='-Duser.language=es -Duser.country=ES -Xms1024m -Xmx2048m'
  ;;

test-fi)
  mvn -e --no-transfer-progress clean integration-test failsafe:verify \
    -Dsurefire.options='-Duser.language=fi -Duser.country=FI -Xms1024m -Xmx2048m'
  ;;

test-fr)
  mvn -e --no-transfer-progress clean integration-test failsafe:verify \
    -Dsurefire.options='-Duser.language=fr -Duser.country=FR -Xms1024m -Xmx2048m'
  ;;

test-zh)
  mvn -e --no-transfer-progress clean integration-test failsafe:verify \
    -Dsurefire.options='-Duser.language=zh -Duser.country=CN -Xms1024m -Xmx2048m'
  ;;

test-ja)
  mvn -e --no-transfer-progress clean integration-test failsafe:verify \
    -Dsurefire.options='-Duser.language=ja -Duser.country=JP -Xms1024m -Xmx2048m'
  ;;

test-pt)
  mvn -e --no-transfer-progress clean integration-test failsafe:verify \
    -Dsurefire.options='-Duser.language=pt -Duser.country=PT -Xms1024m -Xmx2048m'
  ;;

test-tr)
  mvn -e --no-transfer-progress clean integration-test failsafe:verify \
    -Dsurefire.options='-Duser.language=tr -Duser.country=TR -Xms1024m -Xmx2048m'
  ;;

test-ru)
  mvn -e --no-transfer-progress clean integration-test failsafe:verify \
    -Dsurefire.options='-Duser.language=ru -Duser.country=RU -Xms1024m -Xmx2048m'
  ;;

test-al)
  mvn -e --no-transfer-progress clean integration-test failsafe:verify \
    -Dsurefire.options='-Duser.language=sq -Duser.country=AL -Xms1024m -Xmx2048m'
  ;;

versions)
  if [ -v TRAVIS_EVENT_TYPE ] && [ "$TRAVIS_EVENT_TYPE" != "cron" ] ; then exit 0; fi
  mvn -e --no-transfer-progress clean versions:dependency-updates-report \
    versions:plugin-updates-report
  if [ "$(grep "<nextVersion>" target/*-updates-report.xml | cat | wc -l)" -gt 0 ]; then
    echo "Version reports (dependency-updates-report.xml):"
    cat target/dependency-updates-report.xml
    echo "Version reports (plugin-updates-report.xml):"
    cat target/plugin-updates-report.xml
    echo "New dependency versions:"
    grep -B 7 -A 7 "<nextVersion>" target/dependency-updates-report.xml | cat
    echo "New plugin versions:"
    grep -B 4 -A 7 "<nextVersion>" target/plugin-updates-report.xml | cat
    echo "Verification is failed."
    false
  else
    echo "No new versions found"
  fi
  ;;

markdownlint)
  mdl -g . && echo "All .md files verified"
  ;;

no-error-pmd)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo "CS_version: ${CS_POM_VERSION}"
  mvn -e --no-transfer-progress clean install -Pno-validations
  echo "Checkout target sources ..."
  #  checkout_from "https://github.com/pmd/build-tools.git"
  checkout_from "https://github.com/kkoutsilis/build-tools.git"
  cd .ci-temp/build-tools/
  git ls-remote
  git checkout "66d""ed33c74662cb3da612f3d34a5ae""fa""a629b443"
  mvn -e --no-transfer-progress install
  cd ..
  git clone https://github.com/kkoutsilis/pmd.git
  cd pmd
  git ls-remote
  git checkout "fa6a862ac8278906d7bcf21852f6552d27a46a73"
  ./mvnw verify --show-version --errors --batch-mode --no-transfer-progress \
                -DskipTests \
                -Dmaven.javadoc.skip=true \
                -Dmaven.source.skip=true \
                -Dpmd.skip=true \
                -Dcheckstyle.skip=false \
                -Dcheckstyle.version="${CS_POM_VERSION}"
  cd ..
  removeFolderWithProtectedFiles build-tools
  removeFolderWithProtectedFiles pmd
  ;;

no-violation-test-configurate)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo "CS_version: ${CS_POM_VERSION}"
  mvn -e --no-transfer-progress clean install -Pno-validations
  echo "Checkout target sources ..."
  mkdir -p .ci-temp
  cd .ci-temp
  git clone https://github.com/SpongePowered/Configurate.git
  cd Configurate
  ./gradlew -PcheckstyleVersion="${CS_POM_VERSION}" -x test check
  cd ..
  removeFolderWithProtectedFiles Configurate
  ;;

no-violation-test-josm)
  CS_POM_VERSION="10.21.2"  # Hardcode to a valid version
  echo "CS_version: ${CS_POM_VERSION}"
  mvn -e --no-transfer-progress clean install -Passembly,no-validations
  echo "Checkout target sources ..."
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
  ANTLR4_VERSION="$(getMavenProperty 'antlr4.version')"
  echo "version:${CS_POM_VERSION} antlr4:${ANTLR4_VERSION}"
  mvn -e --no-transfer-progress clean install -Pno-validations
  echo "Checkout target sources ..."
  checkout_from "https://github.com/kkoutsilis/xwiki-commons"
  cd .ci-temp/xwiki-commons
  git ls-remote
  git checkout "88f75d13376587956a5e5bd""dad0fa003383f190a"
  # Build custom Checkstyle rules
  mvn -e --no-transfer-progress -f \
    xwiki-commons-tools/xwiki-commons-tool-verification-resources/pom.xml \
    install -DskipTests -Dcheckstyle.version="${CS_POM_VERSION}" \
      -Dantlr4.version="${ANTLR4_VERSION}"
  # Validate xwiki-commons
  mvn -e --no-transfer-progress checkstyle:check@default -Dcheckstyle.version="${CS_POM_VERSION}"
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
    install -Dmaven.test.skip -Dcheckstyle.version="${CS_POM_VERSION}"
  mvn -e --no-transfer-progress -f xwiki-commons-tools/xwiki-commons-tool-xar/pom.xml \
    install -Dmaven.test.skip -Dcheckstyle.version="${CS_POM_VERSION}"
  cd ..
  removeFolderWithProtectedFiles xwiki-commons
  cd ..
  checkout_from https://github.com/xwiki/xwiki-rendering.git
  cd .ci-temp/xwiki-rendering
  # Validate xwiki-rendering
  mvn -e --no-transfer-progress checkstyle:check@default -Dcheckstyle.version="${CS_POM_VERSION}"
  cd ..
  removeFolderWithProtectedFiles xwiki-rendering
  cd ..
  checkout_from https://github.com/kkoutsilis/xwiki-platform.git
  cd .ci-temp/xwiki-platform
  git ls-remote
  git checkout "01848f""ca559805b535559b7b94119a95990a2b5c"
  # Validate xwiki-platform
  mvn -e --no-transfer-progress checkstyle:check@default -Dcheckstyle.version="${CS_POM_VERSION}"
  cd ..
  removeFolderWithProtectedFiles xwiki-platform
  ;;

no-error-test-sbe)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo version:"$CS_POM_VERSION"
  mvn -e --no-transfer-progress clean install -Pno-validations
  echo "Checkout target sources ..."
  checkout_from https://github.com/real-logic/simple-binary-encoding.git
  cd .ci-temp/simple-binary-encoding
  sed -i'' \
    "s/'com.puppycrawl.tools:checkstyle:.*'/'com.puppycrawl.tools:checkstyle:$CS_POM_VERSION'/" \
    build.gradle
  ./gradlew build --stacktrace
  cd ..
  removeFolderWithProtectedFiles simple-binary-encoding
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
  xmlstarlet fo -D \
    -n $working_dir/checks-nonjavadoc-error.xml \
    | xmlstarlet sel --net --template -m .//module -n -v "@name" \
    | grep -vE $MODULES_WITH_EXTERNAL_FILES | grep -v "^$" > $working_dir/temp.txt
  xmlstarlet fo -D \
     -n $working_dir/checks-only-javadoc-error.xml \
    | xmlstarlet sel --net --template -m .//module -n -v "@name" \
    | grep -vE $MODULES_WITH_EXTERNAL_FILES | grep -v "^$" >> $working_dir/temp.txt
  sort $working_dir/temp.txt | uniq | sed "s/Check$//" > $working_dir/web.txt

  xmlstarlet fo -D -n config/checkstyle-checks.xml \
    | xmlstarlet sel --net --template -m .//module -n -v "@name" \
    | grep -vE $MODULES_WITH_EXTERNAL_FILES | grep -v "^$" \
    | sort | uniq | sed "s/Check$//" > $working_dir/file.txt

  DIFF_TEXT=$(diff -u $working_dir/web.txt $working_dir/file.txt | cat)
  fail=0

  if [[ $DIFF_TEXT != "" ]]; then
    echo "Diff is detected."
    if [[ $PULL_REQUEST =~ ^([0-9]+)$ ]]; then
      LINK_PR=https://api.github.com/repos/checkstyle/checkstyle/pulls/$PULL_REQUEST
      REGEXP="https://github.com/checkstyle/contribution/pull/"
      PR_DESC=$(curl -s -H "Authorization: token $READ_ONLY_TOKEN" "$LINK_PR" \
                  | jq '.body' | grep $REGEXP | cat )
      echo 'PR Description grepped:'"${PR_DESC:0:180}"
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
      echo 'file config/checkstyle-checks.xml contains Check that is not present at:'
      echo 'https://github.com/checkstyle/contribution/blob/master/checkstyle-tester/checks-nonjavadoc-error.xml'
      echo 'https://github.com/checkstyle/contribution/blob/master/checkstyle-tester/checks-only-javadoc-error.xml'
      echo 'Please add new Check to one of such files to let Check participate in auto testing'
      fail=1;
    fi
  else
    echo "No Diff detected."
  fi
  removeFolderWithProtectedFiles .ci-temp/verify-no-exception-configs
  sleep 5
  exit $fail
  ;;

verify-regexp-id)
  fail=0
  for FILE in config/*_checks.xml
  do
    a=$(grep -c "<module name=\"Regexp.*" "$FILE") || a=0
    b=$(grep "<module name=\"Regexp" -A 1 "$FILE" | grep -c "<property name=\"id\"") || b=0
    if [ "${a}" != "${b}" ]
    then
      echo "Error: $FILE has Regexp modules without id property immediately after module name."
      fail=1
    fi
  done
  cd ..
  exit $fail
  ;;

checkstyle-and-sevntu)
  export MAVEN_OPTS='-Xmx2000m'
  mvn -e --no-transfer-progress clean verify -DskipTests -DskipITs \
    -Dpmd.skip=true -Dspotbugs.skip=true -Djacoco.skip=true
  ;;

spotbugs-and-pmd)
  mkdir -p .ci-temp/spotbugs-and-pmd
  CHECKSTYLE_DIR=$(pwd)
  export MAVEN_OPTS='-Xmx2000m'
  mvn -e --no-transfer-progress clean test-compile pmd:check spotbugs:check
  cd .ci-temp/spotbugs-and-pmd
  grep "Processing_Errors" "$CHECKSTYLE_DIR/target/site/pmd.html" | cat > errors.log
  RESULT=$(cat errors.log | wc -l)
  if [[ $RESULT != 0 ]]; then
    echo "Errors are detected in target/site/pmd.html."
    sleep 5s
  fi
  cd ..
  removeFolderWithProtectedFiles spotbugs-and-pmd
  exit "$RESULT"
;;

site)
  mvn -e --no-transfer-progress clean site -Pno-validations
  ;;

release-dry-run)
  if [ "$(git log -1 | grep -E "\[maven-release-plugin\] prepare release" | cat | wc -l)" -lt 1 ]
  then
    mvn -e --no-transfer-progress release:prepare -DdryRun=true --batch-mode \
    -Darguments='-DskipTests -DskipITs -Djacoco.skip=true -Dpmd.skip=true \
      -Dspotbugs.skip=true -Dxml.skip=true -Dcheckstyle.ant.skip=true \
      -Dcheckstyle.skip=true -Dgpg.skip=true --no-transfer-progress'
    mvn -e --no-transfer-progress release:clean
  fi
  ;;

assembly-run-all-jar)
  mvn -e --no-transfer-progress clean package -Passembly,no-validations
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo version:"$CS_POM_VERSION"
  mkdir -p .ci-temp
  FOLDER=src/it/resources/com/google/checkstyle/test/chapter7javadoc/rule73wherejavadocrequired
  FILE=InputMissingJavadocTypeCorrect.java
  java -jar target/checkstyle-"$CS_POM_VERSION"-all.jar -c /google_checks.xml \
        $FOLDER/$FILE > .ci-temp/output.log
  fail=0
  if grep -vE '(Starting audit)|(warning)|(Audit done.)' .ci-temp/output.log ; then
    fail=1;
  elif grep 'warning' .ci-temp/output.log ; then
    fail=1;
  fi
  rm .ci-temp/output.log
  sleep 5
  exit $fail
  ;;

check-since-version)
  # Travis merges the PR commit into origin/master
  # This identifies the PR's original commit
  # if it notices a merge commit
  HEAD=$(git rev-parse HEAD)
  if git show --summary HEAD | grep ^Merge: ; then
      echo "Merge detected."
      HEAD=$(git log -n 1 --no-merges --pretty=format:"%H")
  fi
  # Identify previous commit to know how much to examine
  # Script assumes we are only working with 1 commit if we are in master
  # Otherwise, it looks for the common ancestor with master
  COMMIT=$(git rev-parse "$HEAD")
  echo "PR commit: $COMMIT"

  HEAD_NEW_FILES=$(git show "$COMMIT" | cat | grep -A 1 "\-\-\- /dev/null" | cat)
  echo "New files in commit: $HEAD_NEW_FILES"
  MODULE_REG=".*(Check|Filter).java"
  REGEXP="b/src/main/java/com/puppycrawl/tools/checkstyle/(checks|filters|filefilters)/$MODULE_REG"
  NEW_CHECK_FILE=$(git show "$COMMIT" | cat | grep -A 1 "\-\-\- /dev/null" | cat | \
    grep -E "$REGEXP" | \
    cat | sed "s/+++ b\///")
  echo "New Check file: $NEW_CHECK_FILE"

  if [ -f "$NEW_CHECK_FILE" ]; then
    echo "New Check detected: $NEW_CHECK_FILE"
    CS_RELEASE_VERSION="$(getCheckstylePomVersionWithoutSnapshot)"
    echo "CS Release version: $CS_RELEASE_VERSION"

    if [[ $CS_RELEASE_VERSION != *.0 ]]; then
      echo "Next release version is bug fix '$CS_RELEASE_VERSION', we will bump second digit in it";
      MAJOR=$(echo "$CS_RELEASE_VERSION" | cut -d. -f1)
      MINOR=$(echo "$CS_RELEASE_VERSION" | cut -d. -f2)
      PATCH=$(echo "$CS_RELEASE_VERSION" | cut -d. -f3)
      CS_RELEASE_VERSION="$MAJOR""."$((MINOR+1))".0"
      echo "Expected CS Release version after merge of target commit: $CS_RELEASE_VERSION"
    fi

    echo "Grep for @since $CS_RELEASE_VERSION"
    grep "* @since $CS_RELEASE_VERSION" "$NEW_CHECK_FILE"
  else
    echo "No new Check, all is good."
  fi
  ;;

javac11)
  # InputCustomImportOrderNoPackage2 - nothing is required in front of first import
  # InputIllegalTypePackageClassName - bad import for testing
  # InputVisibilityModifierPackageClassName - bad import for testing
  files=($(grep -REL --include='*.java' \
        --exclude='InputCustomImportOrderNoPackage2.java' \
        --exclude='InputIllegalTypePackageClassName.java' \
        --exclude='InputVisibilityModifierPackageClassName.java' \
        '//non-compiled (syntax|with javac)?\:' \
        src/test/resources-noncompilable \
        src/xdocs-examples/resources-noncompilable))
  mkdir -p target
  for file in "${files[@]}"
  do
    javac -d target "${file}"
  done
  ;;

javac17)
  files=($(grep -Rl --include='*.java' ': Compilable with Java17' \
        src/test/resources-noncompilable \
        src/xdocs-examples/resources-noncompilable || true))
  if [[  ${#files[@]} -eq 0 ]]; then
    echo "No Java17 files to process"
  else
      mkdir -p target
      for file in "${files[@]}"
      do
        javac --release 17 --enable-preview -d target "${file}"
      done
  fi
  ;;

javac19)
  files=($(grep -Rl --include='*.java' ': Compilable with Java19' \
        src/test/resources-noncompilable \
        src/xdocs-examples/resources-noncompilable || true))
  if [[  ${#files[@]} -eq 0 ]]; then
    echo "No Java19 files to process"
  else
      mkdir -p target
      for file in "${files[@]}"
      do
        javac --release 19 --enable-preview -d target "${file}"
      done
  fi
  ;;

javac20)
  files=($(grep -Rl --include='*.java' ': Compilable with Java20' \
        src/test/resources-noncompilable \
        src/xdocs-examples/resources-noncompilable || true))
  if [[  ${#files[@]} -eq 0 ]]; then
    echo "No Java20 files to process"
  else
      mkdir -p target
      for file in "${files[@]}"
      do
        javac --release 20 --enable-preview -d target "${file}"
      done
  fi
  ;;

javac21)
  files=($(grep -Rl --include='*.java' ': Compilable with Java21' \
        src/test/resources-noncompilable \
        src/xdocs-examples/resources-noncompilable || true))
  if [[  ${#files[@]} -eq 0 ]]; then
    echo "No Java21 files to process"
  else
    mkdir -p target
    for file in "${files[@]}"
    do
      javac --release 21 --enable-preview -d target "${file}"
    done
  fi
  ;;

package-site)
  mvn -e --no-transfer-progress package -Passembly,no-validations
  mvn -e --no-transfer-progress site -Dlinkcheck.skip=true
  ;;

sonarqube)
  # token could be generated at https://sonarcloud.io/account/security/
  # execution on local for master:
  # SONAR_TOKEN=xxxxxx ./.ci/validation.sh sonarqube
  # execution on local for non-master:
  # SONAR_TOKEN=xxxxxx PR_NUMBER=xxxxxx PR_BRANCH_NAME=xxxxxx ./.ci/validation.sh sonarqube
  checkForVariable "SONAR_TOKEN"

  if [[ $PR_NUMBER =~ ^([0-9]+)$ ]]; then
      SONAR_PR_VARIABLES="-Dsonar.pullrequest.key=$PR_NUMBER"
      SONAR_PR_VARIABLES+=" -Dsonar.pullrequest.branch=$PR_BRANCH_NAME"
      SONAR_PR_VARIABLES+=" -Dsonar.pullrequest.base=master"
      echo "SONAR_PR_VARIABLES: ""$SONAR_PR_VARIABLES"
  fi

  export MAVEN_OPTS='-Xmx2000m'
  # until https://github.com/checkstyle/checkstyle/issues/11637
  # shellcheck disable=SC2086
  mvn -e --no-transfer-progress -Pno-validations clean package sonar:sonar \
       $SONAR_PR_VARIABLES \
       -Dsonar.host.url=https://sonarcloud.io \
       -Dsonar.login="$SONAR_TOKEN" \
       -Dsonar.projectKey=org.checkstyle:checkstyle \
       -Dsonar.organization=checkstyle
  echo "report-task.txt:"
  cat target/sonar/report-task.txt
  echo "Verification of sonar gate status"
  export SONAR_API_TOKEN=$SONAR_TOKEN
  .ci/sonar-break-build.sh
  ;;

no-error-pgjdbc)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: "${CS_POM_VERSION}"
  mvn -e --no-transfer-progress clean install -Pno-validations
  echo "Checkout target sources ..."
  checkout_from https://github.com/pgjdbc/pgjdbc.git
  cd .ci-temp/pgjdbc
  # pgjdbc easily damage build, we should use stable versions
  git checkout "fcc13e70e6b6bb64b848df4b4ba6b3566b5""e95a3"
  ./gradlew --no-parallel --no-daemon checkstyleAll \
            -PenableMavenLocal -Pcheckstyle.version="${CS_POM_VERSION}"
  cd ../
  removeFolderWithProtectedFiles pgjdbc
  ;;

no-error-orekit)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: "${CS_POM_VERSION}"
  mvn -e --no-transfer-progress clean install -Pno-validations
  echo "Checkout target sources ..."
  checkout_from https://github.com/Hipparchus-Math/hipparchus.git
  cd .ci-temp/hipparchus
  # checkout to version that Orekit expects
  SHA_HIPPARCHUS="815ad2bf9ce764e4498911d2145c49165f5f3333"
  git checkout $SHA_HIPPARCHUS
  mvn -e --no-transfer-progress install -DskipTests
  cd -
  checkout_from https://github.com/CS-SI/Orekit.git
  cd .ci-temp/Orekit
  # no CI is enforced in project, so to make our build stable we should
  # checkout to latest release/development (annotated tag or hash) or sha that have fix we need
  # git checkout $(git describe --abbrev=0 --tags)
  git checkout "a32b4629b2890fc198b19a95a714d67b87d7943d"
  mvn -e --no-transfer-progress compile checkstyle:check \
    -Dorekit.checkstyle.version="${CS_POM_VERSION}"
  cd ..
  removeFolderWithProtectedFiles Orekit
  removeFolderWithProtectedFiles hipparchus
  ;;

no-error-hibernate-search)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: "${CS_POM_VERSION}"
  mvn -e --no-transfer-progress clean install -Pno-validations
  echo "Checkout target sources ..."
  checkout_from https://github.com/hibernate/hibernate-search.git
  cd .ci-temp/hibernate-search
  mvn -e --no-transfer-progress clean install -pl build/config -am \
     -DskipTests=true -Dmaven.compiler.failOnWarning=false \
     -Dcheckstyle.skip=true -Dforbiddenapis.skip=true \
     -Dversion.com.puppycrawl.tools.checkstyle="${CS_POM_VERSION}"
  mvn -e --no-transfer-progress checkstyle:check \
     -Dversion.com.puppycrawl.tools.checkstyle="${CS_POM_VERSION}"
  cd ../
  removeFolderWithProtectedFiles hibernate-search
  ;;

no-error-checkstyles-sevntu)
  set -e
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: "${CS_POM_VERSION}"
  mvn -e --no-transfer-progress clean install -Pno-validations
  mvn -e --no-transfer-progress compile verify -Psevntu \
    -Dmaven.sevntu-checkstyle-check.checkstyle.version="${CS_POM_VERSION}" \
    -Dmaven.test.skip=true -Dpmd.skip=true -Dspotbugs.skip=true \
    -Djacoco.skip=true -Dforbiddenapis.skip=true -Dxml.skip=true
  ;;

no-error-sevntu-checks)
  set -e
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: "${CS_POM_VERSION}"
  mvn -e --no-transfer-progress clean install -Pno-validations
  echo "Checkout target sources ..."
  checkout_from https://github.com/sevntu-checkstyle/sevntu.checkstyle.git
  cd .ci-temp/sevntu.checkstyle/sevntu-checks
  mvn -e --no-transfer-progress -Pno-validations verify  -Dcheckstyle.ant.skip=false \
     -Dcheckstyle.version="${CS_POM_VERSION}" \
     -Dcheckstyle.configLocation=../../../config/checkstyle-checks.xml \
     -Dcheckstyle.nonMain.configLocation=../../../config/checkstyle-non-main-files-checks.xml \
     -Dcheckstyle.non-main-files-suppressions.file=config/checkstyle-non-main-files-suppressions.xml
  cd ../../
  removeFolderWithProtectedFiles sevntu.checkstyle
  ;;

no-error-contribution)
  set -e
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: "${CS_POM_VERSION}"
  mvn -e --no-transfer-progress clean install -Pno-validations
  echo "Checkout target sources ..."
  checkout_from https://github.com/checkstyle/contribution.git
  cd .ci-temp/contribution
  cd patch-diff-report-tool
  mvn -e --no-transfer-progress verify -DskipTests -Dcheckstyle.version="${CS_POM_VERSION}" \
     -Dcheckstyle.configLocation=../../../config/checkstyle-checks.xml
  cd ../
  cd releasenotes-builder
  mvn -e --no-transfer-progress verify -DskipTests -Dcheckstyle.version="${CS_POM_VERSION}" \
     -Dcheckstyle.configLocation=../../../config/checkstyle-checks.xml
  cd ../../
  removeFolderWithProtectedFiles contribution
  ;;

no-error-methods-distance)
  set -e
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: "${CS_POM_VERSION}"
  mvn -e --no-transfer-progress clean install -Pno-validations
  echo "Checkout target sources ..."
  checkout_from https://github.com/sevntu-checkstyle/methods-distance.git
  cd .ci-temp/methods-distance
  mvn -e --no-transfer-progress verify -DskipTests -Dcheckstyle-version="${CS_POM_VERSION}" \
     -Dcheckstyle.configLocation=../../config/checkstyle-checks.xml
  cd ..
  removeFolderWithProtectedFiles  methods-distance
  ;;

no-error-equalsverifier)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: "${CS_POM_VERSION}"
  mvn -e --no-transfer-progress clean install -Pno-validations
  echo "Checkout target sources ..."
  checkout_from https://github.com/jqno/equalsverifier.git
  cd .ci-temp/equalsverifier
  mvn -e --no-transfer-progress -Pstatic-analysis-checkstyle compile \
    checkstyle:check -Dversion.checkstyle="${CS_POM_VERSION}"
  cd ../
  removeFolderWithProtectedFiles equalsverifier
  ;;

no-error-strata)
  set -e
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: "${CS_POM_VERSION}"
  mvn -e --no-transfer-progress clean install -Pno-validations
  echo "Checkout target sources ..."
  checkout_from https://github.com/OpenGamma/Strata.git
  cd .ci-temp/Strata
  # shellcheck disable=2016 # we do not want to expand properties in this command
  STRATA_CS_POM_VERSION=$(mvn -e --no-transfer-progress -q -Dexec.executable='echo' \
                     -Dexec.args='${checkstyle.version}' \
                     --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
  mvn -e --no-transfer-progress install -B -Dstrict -DskipTests \
     -Dforbiddenapis.skip=true -Dcheckstyle.version="${CS_POM_VERSION}" \
     -Dcheckstyle.config.suffix="-v$STRATA_CS_POM_VERSION"
  cd ../
  removeFolderWithProtectedFiles Strata
  ;;

no-error-spring-integration)
  set -e
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: "${CS_POM_VERSION}"
  mvn -e --no-transfer-progress clean install -Pno-validations
  echo "Checkout target sources ..."
  checkout_from https://github.com/spring-projects/spring-integration.git
  cd .ci-temp/spring-integration
  PROP_MAVEN_LOCAL="mavenLocal"
  PROP_CS_VERSION="checkstyleVersion"
  ./gradlew clean check --parallel -x test -P$PROP_MAVEN_LOCAL \
    -P$PROP_CS_VERSION="${CS_POM_VERSION}"
  cd ../
  removeFolderWithProtectedFiles spring-integration
  ;;

no-error-htmlunit)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: "${CS_POM_VERSION}"
  mvn -e --no-transfer-progress clean install -Pno-validations
  echo "Checkout target sources ..."
  checkout_from https://github.com/HtmlUnit/htmlunit
  cd .ci-temp/htmlunit
  mvn -e --no-transfer-progress compile checkstyle:check -Dcheckstyle.version="${CS_POM_VERSION}"
  cd ../
  removeFolderWithProtectedFiles htmlunit
  ;;

no-error-spotbugs)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: "${CS_POM_VERSION}"
  mvn -e --no-transfer-progress clean install -Pno-validations
  echo "Checkout target sources ..."
  checkout_from https://github.com/spotbugs/spotbugs
  cd .ci-temp/spotbugs
  sed -i'' "s/mavenCentral()/mavenLocal(); mavenCentral()/" build.gradle
  sed -i'' "s/toolVersion.*$/toolVersion '${CS_POM_VERSION}'/" gradle/checkstyle.gradle
  ./gradlew :eclipsePlugin-junit:checkstyleTest -Dcheckstyle.version="${CS_POM_VERSION}"
  cd ../
  removeFolderWithProtectedFiles spotbugs
  ;;

no-exception-struts)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  echo CS_version: "${CS_POM_VERSION}"
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i'' 's/#apache-struts/apache-struts/' projects-to-test-on.properties
  groovy ./diff.groovy --listOfProjects projects-to-test-on.properties \
      --patchConfig checks-nonjavadoc-error.xml  -p "$BRANCH" -r ../../..  \
      --useShallowClone \
      --allowExcludes --mode single -xm "-Dcheckstyle.failsOnError=false"
  cd ../../
  removeFolderWithProtectedFiles contribution
  ;;


no-exception-checkstyle-sevntu)
  set -e
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  echo CS_version: "${CS_POM_VERSION}"
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i'' 's/#local-checkstyle/local-checkstyle/' projects-to-test-on.properties
  sed -i'' 's/#sevntu-checkstyle/sevntu-checkstyle/' projects-to-test-on.properties
  groovy ./diff.groovy --listOfProjects projects-to-test-on.properties \
      --patchConfig checks-nonjavadoc-error.xml  -p "$BRANCH" -r ../../..  \
      --useShallowClone \
      --allowExcludes --mode single -xm "-Dcheckstyle.failsOnError=false"
  cd ../../
  removeFolderWithProtectedFiles contribution
  ;;

no-exception-checkstyle-sevntu-javadoc)
  set -e
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  echo CS_version: "${CS_POM_VERSION}"
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i'' 's/#local-checkstyle/local-checkstyle/' projects-to-test-on.properties
  sed -i'' 's/#sevntu-checkstyle/sevntu-checkstyle/' projects-to-test-on.properties
  groovy ./diff.groovy --listOfProjects projects-to-test-on.properties \
      --patchConfig checks-only-javadoc-error.xml  -p "$BRANCH" -r ../../..  \
      --useShallowClone \
      --allowExcludes --mode single -xm "-Dcheckstyle.failsOnError=false"
  cd ../../
  removeFolderWithProtectedFiles contribution
  ;;


no-exception-guava)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: "${CS_POM_VERSION}"
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  sed -i'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i'' 's/#guava/guava/' projects-to-test-on.properties
  groovy ./diff.groovy --listOfProjects projects-to-test-on.properties \
      --patchConfig checks-nonjavadoc-error.xml  -p "$BRANCH" -r ../../..  \
      --useShallowClone \
      --allowExcludes --mode single -xm "-Dcheckstyle.failsOnError=false"
  cd ../../
  removeFolderWithProtectedFiles contribution
  ;;

no-exception-hibernate-orm)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: "${CS_POM_VERSION}"
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#hibernate-orm/hibernate-orm/' projects-to-test-on.properties
  groovy ./diff.groovy --listOfProjects projects-to-test-on.properties \
      --patchConfig checks-nonjavadoc-error.xml  -p "$BRANCH" -r ../../..  \
      --useShallowClone \
      --allowExcludes --mode single -xm "-Dcheckstyle.failsOnError=false"
  cd ../../
  removeFolderWithProtectedFiles contribution
  ;;

no-exception-spotbugs)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  echo CS_version: "${CS_POM_VERSION}"
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#spotbugs/spotbugs/' projects-to-test-on.properties
  groovy ./diff.groovy --listOfProjects projects-to-test-on.properties \
      --patchConfig checks-nonjavadoc-error.xml  -p "$BRANCH" -r ../../..  \
      --useShallowClone \
      --allowExcludes --mode single -xm "-Dcheckstyle.failsOnError=false"
  cd ../../
  removeFolderWithProtectedFiles contribution
  ;;

no-exception-spoon)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  echo CS_version: "${CS_POM_VERSION}"
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#spoon/spoon/' projects-to-test-on.properties
  groovy ./diff.groovy --listOfProjects projects-to-test-on.properties \
      --patchConfig checks-nonjavadoc-error.xml  -p "$BRANCH" -r ../../..  \
      --useShallowClone \
      --allowExcludes --mode single -xm "-Dcheckstyle.failsOnError=false"
  cd ../../
  removeFolderWithProtectedFiles contribution
  ;;

no-exception-spring-framework)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  echo CS_version: "${CS_POM_VERSION}"
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#spring-framework/spring-framework/' projects-to-test-on.properties
  groovy ./diff.groovy --listOfProjects projects-to-test-on.properties \
      --patchConfig checks-nonjavadoc-error.xml  -p "$BRANCH" -r ../../..  \
      --useShallowClone \
      --allowExcludes --mode single -xm "-Dcheckstyle.failsOnError=false"
  cd ../../
  removeFolderWithProtectedFiles contribution
  ;;

no-exception-hbase)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: "${CS_POM_VERSION}"
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#Hbase/Hbase/' projects-to-test-on.properties
  groovy ./diff.groovy --listOfProjects projects-to-test-on.properties \
      --patchConfig checks-nonjavadoc-error.xml  -p "$BRANCH" -r ../../..  \
      --useShallowClone \
      --allowExcludes --mode single -xm "-Dcheckstyle.failsOnError=false"
  cd ../../
  removeFolderWithProtectedFiles contribution
  ;;

no-exception-Pmd-elasticsearch-lombok-ast)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  echo CS_version: "${CS_POM_VERSION}"
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#pmd/pmd/' projects-to-test-on.properties
  sed -i.'' 's/#elasticsearch/elasticsearch/' projects-to-test-on.properties
  sed -i.'' 's/#lombok-ast/lombok-ast/' projects-to-test-on.properties
  groovy ./diff.groovy --listOfProjects projects-to-test-on.properties \
      --patchConfig checks-nonjavadoc-error.xml  -p "$BRANCH" -r ../../..  \
      --useShallowClone \
      --allowExcludes --mode single -xm "-Dcheckstyle.failsOnError=false"
  cd ../../
  removeFolderWithProtectedFiles contribution
  ;;

no-exception-alot-of-projects)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: "${CS_POM_VERSION}"
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  sed -i.'' 's/^guava/#guava/' projects-to-test-on.properties
  sed -i.'' 's/#RxJava/RxJava/' projects-to-test-on.properties
  sed -i.'' 's/#java-design-patterns/java-design-patterns/' projects-to-test-on.properties
  sed -i.'' 's/#MaterialDesignLibrary/MaterialDesignLibrary/' projects-to-test-on.properties
  sed -i.'' 's/#apache-ant/apache-ant/' projects-to-test-on.properties
  sed -i.'' 's/#apache-jsecurity/apache-jsecurity/' projects-to-test-on.properties
  sed -i.'' 's/#android-launcher/android-launcher/' projects-to-test-on.properties
  groovy ./diff.groovy --listOfProjects projects-to-test-on.properties \
      --patchConfig checks-nonjavadoc-error.xml  -p "$BRANCH" -r ../../.. \
      --useShallowClone \
      --allowExcludes --mode single -xm "-Dcheckstyle.failsOnError=false"
  cd ../../
  removeFolderWithProtectedFiles contribution
  ;;

no-warning-imports-guava)
  PROJECTS=checks-import-order/projects-to-test-imports-guava.properties
  CONFIG=checks-import-order/checks-imports-error-guava.xml
  REPORT=reports/guava/site/index.html
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  echo CS_version: "${CS_POM_VERSION}"
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  groovy ./diff.groovy --listOfProjects $PROJECTS --patchConfig $CONFIG \
      --allowExcludes -p "$BRANCH" -r ../../.. \
      --useShallowClone \
      --mode single -xm "-Dcheckstyle.failsOnError=false"
  RESULT=$(grep -A 5 "&#160;Warning</td>" $REPORT | cat)
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
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  BRANCH=$(git rev-parse --abbrev-ref HEAD)
  echo CS_version: "${CS_POM_VERSION}"
  checkout_from https://github.com/checkstyle/contribution
  cd .ci-temp/contribution/checkstyle-tester
  groovy ./diff.groovy --listOfProjects $PROJECTS --patchConfig $CONFIG \
      --allowExcludes -p "$BRANCH" -r ../../..\
      --useShallowClone \
      --mode single
  RESULT=$(grep -A 5 "&#160;Warning</td>" $REPORT | cat)
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

git-diff)
  if [ "$(git status | grep 'Changes not staged\|Untracked files')" ]; then
    printf "Please clean up or update .gitattributes file.\nGit status output:\n"
    printf "Top 300 lines of diff:\n"
    git status
    git diff | head -n 300
    false
  fi
  ;;

git-no-merge-commits)
  MERGE_COMMITS=$(git rev-list --merges master.."$PR_HEAD_SHA")

  if [ -n "$MERGE_COMMITS" ]; then
    for MERGE_COMMIT in $MERGE_COMMITS; do
      echo "Merge commit found in PR: $MERGE_COMMIT"
    done
    echo "To learn how to clean up your commit history, visit:"
    echo "https://checkstyle.org/beginning_development.html#Starting_Development"
    exit 1
  fi
  ;;

git-check-pull-number)
  COMMITS="$(git log --format=format:%B master.."$PR_HEAD_SHA")"

  echo "$COMMITS" | while read -r COMMIT ; do
    if [[ $COMMIT =~ 'Pull #' ]]; then
      PULL_MESSAGE_NUMBER=$(echo "$COMMIT" | cut -d'#' -f 2 | cut -d':' -f 1)
      if [[ $PULL_MESSAGE_NUMBER != "$PR_NUMBER" ]]; then
        echo "Referenced PR and this PR number do not match."
        echo "Commit message should reference $PR_NUMBER"
        exit 1
      fi
    fi
  done
  ;;

jacoco)
  export MAVEN_OPTS='-Xmx2000m'
  mvn -e --no-transfer-progress clean test \
    jacoco:restore-instrumented-classes \
    jacoco:report@default-report \
    jacoco:check@default-check
  # if launch is not from CI, we skip this step
  if [[ $CI == 'true' ]]; then
    echo "Reporting to codecov"
    bash <(curl --fail-with-body -s https://codecov.io/bash)
  else
    echo "Result is at target/site/jacoco/index.html"
  fi
  ;;

ci-temp-check)
    fail=0
    mkdir -p .ci-temp
    if [ -z "$(ls -A .ci-temp)" ]; then
        echo "Folder .ci-temp/ is empty."
    else
        echo "Folder .ci-temp/ is not empty. Verification failed."
        echo "Contents of .ci-temp/:"
        fail=1
    fi
    ls -A .ci-temp
    exit $fail
  ;;

  check-github-workflows-concurrency)
    GITHUB_WORKFLOW_FILES=$(find .github/workflows -maxdepth 1 -not -type d -name "*.y*ml")

    FILES_NO_CONCURRENCY=()
    for f in $GITHUB_WORKFLOW_FILES; do
      if ! grep -wq "concurrency:" "$f"; then
            FILES_NO_CONCURRENCY+=( $f )
      fi
    done

    if [[ ${#FILES_NO_CONCURRENCY[@]} -gt 0 ]]; then
      echo "The following Github workflows are missing a concurrency block:"
    fi

    for value in "${FILES_NO_CONCURRENCY[@]}"; do
      echo "$value"
    done

    exit ${#FILES_NO_CONCURRENCY[@]}
    ;;

check-wildcards-on-pitest-target-classes)
  ALL_CLASSES=$(xmlstarlet sel \
    -N x=http://maven.apache.org/POM/4.0.0 \
    -t -v "/x:project/x:profiles/x:profile//x:targetClasses/x:param" \
    -n pom.xml)

  CLASSES_NO_WILDCARD=$(echo "$ALL_CLASSES" | grep -v ".*\*\$" | grep -v -e '^\s*$' || echo)
  CLASSES_NO_WILDCARD_COUNT=$(echo "$CLASSES_NO_WILDCARD" | grep -v -e '^\s*$' | wc -l)

  if [[ "$CLASSES_NO_WILDCARD_COUNT" -gt 0 ]]; then
    echo "Append asterisks to the following pitest target classes in pom.xml:"
    echo "$CLASSES_NO_WILDCARD"
  fi
  exit "$CLASSES_NO_WILDCARD_COUNT"
  ;;

verify)
  mvn -e --no-transfer-progress clean verify
  ;;

package-all-jar)
  mvn -e --no-transfer-progress clean package -Passembly
  ;;

website-only)
  mvn -e --no-transfer-progress clean site -Pno-validations
  ;;

pmd)
  mvn -e --no-transfer-progress clean test-compile pmd:check
  ;;

spotbugs)
  mvn -e --no-transfer-progress clean test-compile spotbugs:check
  ;;

checkstyle)
  mvn -e --no-transfer-progress clean compile antrun:run@ant-phase-verify
  ;;

forbiddenapis)
  mvn -e --no-transfer-progress \
    clean compile test-compile forbiddenapis:testCheck@forbiddenapis-test
  ;;

run-test)
  if [[ -z "$2" ]] ; then
    echo "Error: test class is not defined."
    echo "Example: mvn -e --no-transfer-progress clean test -Dtest=XdocsPagesTest,XdocsJavaDocsTest"
    echo "Example: mvn -e --no-transfer-progress clean test -Dtest=CheckerTest#testDestroy"
    exit 1
  fi
  mvn -e --no-transfer-progress clean test -Dtest="$2"
  ;;

sevntu)
  mvn -e --no-transfer-progress clean compile checkstyle:check@sevntu-checkstyle-check
  ;;

*)
  echo "Unexpected argument: $1"
  echo "Supported tasks:"
  list_tasks "${0}"
  false
  ;;

esac

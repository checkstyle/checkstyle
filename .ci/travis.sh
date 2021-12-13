#!/bin/bash
# Attention, there is no "-x" to avoid problems on Travis
set -e

source ./.ci/util.sh

export RUN_JOB=1

case $1 in

init-m2-repo)
  if [[ $RUN_JOB == 1 ]]; then
    MVN_SETTINGS=${TRAVIS_HOME}/.m2/settings.xml
    if [[ -f ${MVN_SETTINGS} ]]; then
      if [[ $TRAVIS_OS_NAME == 'osx' ]]; then
        sed -i'' -e "/<mirrors>/,/<\/mirrors>/ d" $MVN_SETTINGS
      else
        xmlstarlet ed --inplace -d "//mirrors" $MVN_SETTINGS
      fi
    fi
    if [[ $USE_MAVEN_REPO == 'true' && ! -d "~/.m2" ]]; then
     echo "Maven local repo cache is not found, initializing it ..."
     mvn -e --no-transfer-progress -B install -Pno-validations;
     mvn -e --no-transfer-progress clean;
    fi
  else
    echo "$1 is skipped";
  fi
  ;;

install-custom-mvn)
  if [[ -n "${CUSTOM_MVN_VERSION}" ]]; then
    echo "Download Maven ${CUSTOM_MVN_VERSION}....";
    URL="https://archive.apache.org/dist/maven/maven-3/"
    URL=$URL"${CUSTOM_MVN_VERSION}/binaries/apache-maven-${CUSTOM_MVN_VERSION}-bin.zip"
    wget $URL
    unzip -q apache-maven-${CUSTOM_MVN_VERSION}-bin.zip
    export M2_HOME=$PWD/apache-maven-${CUSTOM_MVN_VERSION};
    export PATH=$M2_HOME/bin:$PATH;
  fi
  ;;

run-command)
  if [[ $RUN_JOB == 1 ]]; then
    echo "eval of CMD is starting";
    echo "CMD=$2";
    eval $2;
    echo "eval of CMD is completed";
  fi
  ;;

run-command-after-success)
  if [[ -n $CMD_AFTER_SUCCESS
        && $RUN_JOB == 1
     ]];
  then
      echo "CMD_AFTER_SUCCESS is starting";
      eval $CMD_AFTER_SUCCESS;
      echo "CMD_AFTER_SUCCESS is finished";
  fi
  ;;

deploy-snapshot)
  SKIP_DEPLOY=false
  if [ $(git log -1 | grep -E "\[maven-release-plugin\] prepare release" | cat | wc -l) -lt 1 ];
    then
      SKIP_DEPLOY=false;
    else
      SKIP_DEPLOY=true;
  fi;
  if [[ $TRAVIS_REPO_SLUG == 'checkstyle/checkstyle'
          && $TRAVIS_BRANCH == 'master'
          && $TRAVIS_PULL_REQUEST == 'false'
          && $DEPLOY == 'true'
          && $RUN_JOB == 1
          && $SKIP_DEPLOY == 'false'
     ]];
  then
      mvn -e --no-transfer-progress -s config/deploy-settings.xml -Pno-validations deploy;
      echo "deploy to maven snapshot repository is finished";
  fi
  sleep 5s
  ;;

git-diff)
  if [ "$(git status | grep 'Changes not staged\|Untracked files')" ]; then
    printf "Please clean up or update .gitattributes file.\nGit status output:\n"
    git status
    printf "Top 300 lines of diff:\n"
    git diff | head -n 300
    sleep 5s
    false
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
    sleep 5s
    exit $fail
  ;;

checkstyle-and-sevntu)
  export MAVEN_OPTS='-Xmx2000m'
  mvn -e --no-transfer-progress clean verify -DskipTests -DskipITs --no-transfer-progress \
    -Dpmd.skip=true -Dspotbugs.skip=true -Djacoco.skip=true
  ;;

jacoco)
  export MAVEN_OPTS='-Xmx2000m'
  mvn -e --no-transfer-progress clean test \
    jacoco:restore-instrumented-classes \
    jacoco:report@default-report \
    jacoco:check@default-check
  # BUILD_REASON is variable from CI, if launch is not from CI, we skip this step
  if [ -n "$BUILD_REASON" ];then
    bash <(curl -s https://codecov.io/bash)
  fi
  ;;

test)
  mvn -e --no-transfer-progress clean integration-test failsafe:verify \
  -DargLine='-Xms1024m -Xmx2048m'
  ;;

test-de)
  mvn -e --no-transfer-progress clean integration-test failsafe:verify \
    -DargLine='-Duser.language=de -Duser.country=DE -Xms1024m -Xmx2048m'
  ;;

test-es)
  mvn -e --no-transfer-progress clean integration-test failsafe:verify \
    -DargLine='-Duser.language=es -Duser.country=ES -Xms1024m -Xmx2048m'
  ;;

test-fi)
  mvn -e --no-transfer-progress clean integration-test failsafe:verify \
    -DargLine='-Duser.language=fi -Duser.country=FI -Xms1024m -Xmx2048m'
  ;;

test-fr)
  mvn -e --no-transfer-progress clean integration-test failsafe:verify \
    -DargLine='-Duser.language=fr -Duser.country=FR -Xms1024m -Xmx2048m'
  ;;

test-zh)
  mvn -e --no-transfer-progress clean integration-test failsafe:verify \
    -DargLine='-Duser.language=zh -Duser.country=CN -Xms1024m -Xmx2048m'
  ;;

test-ja)
  mvn -e --no-transfer-progress clean integration-test failsafe:verify \
    -DargLine='-Duser.language=ja -Duser.country=JP -Xms1024m -Xmx2048m'
  ;;

test-pt)
  mvn -e --no-transfer-progress clean integration-test failsafe:verify \
    -DargLine='-Duser.language=pt -Duser.country=PT -Xms1024m -Xmx2048m'
  ;;

test-tr)
  mvn -e --no-transfer-progress clean integration-test failsafe:verify \
    -DargLine='-Duser.language=tr -Duser.country=TR -Xms1024m -Xmx2048m'
  ;;

osx-assembly)
  export JAVA_HOME=$(/usr/libexec/java_home)
  mvn -e --no-transfer-progress package -Passembly
  ;;

osx-package)
  export JAVA_HOME=$(/usr/libexec/java_home)
  mvn -e --no-transfer-progress package
  ;;

osx-jdk13-package)
  export JAVA_HOME=$(/usr/libexec/java_home)
  mvn -e --no-transfer-progress package
  ;;

osx-jdk13-assembly)
  mvn -e --no-transfer-progress package -Passembly
  ;;

site)
  mvn -e --no-transfer-progress clean site -Pno-validations
  ;;

javac8)
  # InputCustomImportOrderNoPackage2 - nothing is required in front of first import
  # InputIllegalTypePackageClassName - bad import for testing
  # InputVisibilityModifierPackageClassName - bad import for testing
  files=($(grep -REL --include='*.java' \
        --exclude='InputCustomImportOrderNoPackage2.java' \
        --exclude='InputIllegalTypePackageClassName.java' \
        --exclude='InputVisibilityModifierPackageClassName.java' \
        '//non-compiled (syntax|with javac)?\:' \
        src/test/resources-noncompilable))
  mkdir -p target
  for file in "${files[@]}"
  do
    javac -d target "${file}"
  done
  ;;

javac9)
  files=($(grep -Rl --include='*.java' ': Compilable with Java9' \
        src/test/resources-noncompilable || true))
  if [[  ${#files[@]} -eq 0 ]]; then
    echo "No Java9 files to process"
  else
      mkdir -p target
      for file in "${files[@]}"
      do
        javac --release 9 -d target "${file}"
      done
  fi
  ;;

javac14)
  files=($(grep -Rl --include='*.java' ': Compilable with Java14' \
        src/test/resources-noncompilable || true))
  if [[  ${#files[@]} -eq 0 ]]; then
    echo "No Java14 files to process"
  else
      mkdir -p target
      for file in "${files[@]}"
      do
        javac --release 14 --enable-preview -d target "${file}"
      done
  fi
  ;;

javac15)
  files=($(grep -Rl --include='*.java' ': Compilable with Java15' \
        src/test/resources-noncompilable || true))
  if [[  ${#files[@]} -eq 0 ]]; then
    echo "No Java15 files to process"
  else
      mkdir -p target
      for file in "${files[@]}"
      do
        javac --release 15 --enable-preview -d target "${file}"
      done
  fi
  ;;

javac16)
  files=($(grep -Rl --include='*.java' ': Compilable with Java16' \
        src/test/resources-noncompilable || true))
  if [[  ${#files[@]} -eq 0 ]]; then
    echo "No Java16 files to process"
  else
      mkdir -p target
      for file in "${files[@]}"
      do
        javac --release 16 --enable-preview -d target "${file}"
      done
  fi
  ;;

jdk14-assembly-site)
  mvn -e --no-transfer-progress package -Passembly
  mvn -e --no-transfer-progress site -Pno-validations
  ;;

jdk14-verify-limited)
  # we skip pmd and spotbugs as they executed in special Travis build
  mvn -e --no-transfer-progress verify -Dpmd.skip=true -Dspotbugs.skip=true
  ;;

versions)
  if [ -v TRAVIS_EVENT_TYPE ] && [ $TRAVIS_EVENT_TYPE != "cron" ] ; then exit 0; fi
  mvn -e --no-transfer-progress clean versions:dependency-updates-report \
    versions:plugin-updates-report
  if [ $(grep "<nextVersion>" target/*-updates-report.xml | cat | wc -l) -gt 0 ]; then
    echo "Version reports (dependency-updates-report.xml):"
    cat target/dependency-updates-report.xml
    echo "Version reports (plugin-updates-report.xml):"
    cat target/plugin-updates-report.xml
    echo "New dependency versions:"
    grep -B 7 -A 7 "<nextVersion>" target/dependency-updates-report.xml | cat
    echo "New plugin versions:"
    grep -B 4 -A 7 "<nextVersion>" target/plugin-updates-report.xml | cat
    echo "Verification is failed."
    sleep 5s
    false
  else
    echo "No new versions found"
  fi
  ;;

assembly-run-all-jar)
  mvn -e --no-transfer-progress clean package -Passembly
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo version:$CS_POM_VERSION
  mkdir -p .ci-temp
  FOLDER=src/it/resources/com/google/checkstyle/test/chapter7javadoc/rule73wherejavadocrequired
  FILE=InputMissingJavadocTypeCheckNoViolations.java
  java -jar target/checkstyle-$CS_POM_VERSION-all.jar -c /google_checks.xml \
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

release-dry-run)
  if [ $(git log -1 | grep -E "\[maven-release-plugin\] prepare release" | cat | wc -l) -lt 1 ];then
    mvn -e --no-transfer-progress release:prepare -DdryRun=true --batch-mode \
    -Darguments='-DskipTests -DskipITs -Djacoco.skip=true -Dpmd.skip=true \
      -Dspotbugs.skip=true -Dxml.skip=true -Dcheckstyle.ant.skip=true \
      -Dcheckstyle.skip=true -Dgpg.skip=true --no-transfer-progress'
    mvn -e --no-transfer-progress release:clean
  fi
  ;;

releasenotes-gen)
  .ci/releasenotes-gen.sh
  ;;

pr-age)
  # Travis merges the PR commit into origin/master
  # This command undoes that to work with the original branch
  # if it notices a merge commit
  if git show --summary HEAD | grep ^Merge: ;
  then
    git reset --hard `git log -n 1 --no-merges --pretty=format:"%h"`
  fi

  PR_MASTER=`git merge-base origin/master HEAD`
  COMMITS_SINCE_MASTER=`git rev-list --count $PR_MASTER..origin/master`
  MAX_ALLOWED=10

  echo "The PR is based on a master that is $COMMITS_SINCE_MASTER commit(s) old."
  echo "The max allowed is $MAX_ALLOWED."

  if (( $COMMITS_SINCE_MASTER > $MAX_ALLOWED ));
  then
    echo "This PR is too old and should be rebased on origin/master."
    sleep 5s
    false
  fi
  ;;

check-chmod)
  .ci/checkchmod.sh
  ;;

no-error-test-sbe)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo version:$CS_POM_VERSION
  mvn -e --no-transfer-progress clean install -Pno-validations
  checkout_from https://github.com/real-logic/simple-binary-encoding.git
  cd .ci-temp/simple-binary-encoding
  sed -i'' \
    "s/'com.puppycrawl.tools:checkstyle:.*'/'com.puppycrawl.tools:checkstyle:$CS_POM_VERSION'/" \
    build.gradle
  ./gradlew build --stacktrace
  cd ..
  removeFolderWithProtectedFiles simple-binary-encoding
  ;;

check-since-version)
  # Travis merges the PR commit into origin/master
  # This identifies the PR's original commit
  # if it notices a merge commit
  HEAD=`git rev-parse HEAD`
  if git show --summary HEAD | grep ^Merge: ; then
      echo "Merge detected."
      HEAD=`git log -n 1 --no-merges --pretty=format:"%H"`
  fi
  # Identify previous commit to know how much to examine
  # Script assumes we are only working with 1 commit if we are in master
  # Otherwise, it looks for the common ancestor with master
  COMMIT=`git rev-parse $HEAD`
  echo "PR commit: $COMMIT"

  HEAD_NEW_FILES=$(git show $COMMIT | cat | grep -A 1 "\-\-\- /dev/null" | cat)
  echo "New files in commit: $HEAD_NEW_FILES"
  MODULE_REG=".*(Check|Filter).java"
  REGEXP="b/src/main/java/com/puppycrawl/tools/checkstyle/(checks|filters|filefilters)/$MODULE_REG"
  NEW_CHECK_FILE=$(git show $COMMIT | cat | grep -A 1 "\-\-\- /dev/null" | cat | \
    grep -E "$REGEXP" | \
    cat | sed "s/+++ b\///")
  echo "New Check file: $NEW_CHECK_FILE"

  if [ -f "$NEW_CHECK_FILE" ]; then
    echo "New Check detected: $NEW_CHECK_FILE"
    CS_POM_VERSION="$(getCheckstylePomVersion)"
    CS_RELEASE_VERSION=$(echo $CS_POM_VERSION | cut -d '-' -f 1)
    echo "CS Release version: $CS_RELEASE_VERSION"
    echo "Grep for @since $CS_RELEASE_VERSION"
    sleep 5s
    grep "* @since $CS_RELEASE_VERSION" $NEW_CHECK_FILE
  else
    echo "No new Check, all is good."
  fi
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

markdownlint)
  mdl -g . && echo "All .md files verified"
  ;;

*)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac

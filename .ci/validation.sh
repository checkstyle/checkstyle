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

function get_outdated_dependencies() {
  local report_xml=$1
  xmlstarlet sel \
    -N d="https://www.mojohaus.org/VERSIONS/DEPENDENCY-UPDATES-REPORT/2.0.0" \
    -t -m "//d:dependency[d:status!='no new available']" \
    -v "d:groupId" -o ":" -v "d:artifactId" -o " " \
    -v "d:currentVersion" -o " -> " -v "d:lastVersion" -n \
    "$report_xml" | sort
}

function get_outdated_plugins() {
  local report_xml=$1
  xmlstarlet sel \
    -N p="https://www.mojohaus.org/VERSIONS/PLUGIN-UPDATES-REPORT/2.0.0" \
    -t -m "//p:plugin[p:status!='no new available']" \
    -v "p:groupId" -o ":" -v "p:artifactId" -o " " \
    -v "p:currentVersion" -o " -> " -v "p:lastVersion" -n \
    "$report_xml" | sort
}

case $1 in

all-sevntu-checks)
  working_dir=.ci-temp/all-sevntu-checks
  mkdir -p $working_dir
  xmlstarlet sel --net --template -m .//module -v "@name" -n config/checkstyle-sevntu-checks.xml \
    | grep -vE "Checker|TreeWalker|Filter|Holder" | grep -v "^$" \
    | sed "s/com\.github\.sevntu\.checkstyle\.checks\..*\.//" \
    | sort | uniq | sed "s/Check$//" > $working_dir/file.txt

  curl --fail-with-body -s http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/allclasses-frame.html \
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
  #  JavadocCommentsLexerUtil, JavadocCommentsParserUtil, and SimpleToken
  #  are excluded from Pitest (aligned with JaCoCo exclusion)
  list=("com.puppycrawl.tools.checkstyle.meta.*"
    "com.puppycrawl.tools.checkstyle.site.*"
    "com.puppycrawl.tools.checkstyle.grammar.JavadocCommentsLexerUtil"
    "com.puppycrawl.tools.checkstyle.grammar.JavadocCommentsParserUtil"
    "com.puppycrawl.tools.checkstyle.grammar.SimpleToken" "${list[@]}")

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
  ./mvnw -e --no-transfer-progress clean compile exec:exec -Peclipse-compiler
  ;;

nondex)
  # Exclude test that fails due to picocli library usage
  SKIPPED_TESTS='!JavadocPropertiesGeneratorTest#testNonExistentArgument,'
  # Exclude test that fails due to stackoverflow error
  SKIPPED_TESTS+='!SingleSpaceSeparatorCheckTest#testNoStackoverflowError'
  ./mvnw -e --no-transfer-progress \
    --fail-never clean nondex:nondex -DargLine='-Xms1g -Xmx2g' \
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
  ./mvnw -e --no-transfer-progress clean integration-test failsafe:verify \
  -DargLine='-Xms1g -Xmx2g'
  ;;

test-de)
  ./mvnw -e --no-transfer-progress clean integration-test failsafe:verify \
    -Dsurefire.options='-Duser.language=de -Duser.country=DE -Xms1g -Xmx2g'
  ;;

test-es)
  ./mvnw -e --no-transfer-progress clean integration-test failsafe:verify \
    -Dsurefire.options='-Duser.language=es -Duser.country=ES -Xms1g -Xmx2g'
  ;;

test-fi)
  ./mvnw -e --no-transfer-progress clean integration-test failsafe:verify \
    -Dsurefire.options='-Duser.language=fi -Duser.country=FI -Xms1g -Xmx2g'
  ;;

test-fr)
  ./mvnw -e --no-transfer-progress clean integration-test failsafe:verify \
    -Dsurefire.options='-Duser.language=fr -Duser.country=FR -Xms1g -Xmx2g'
  ;;

test-zh)
  ./mvnw -e --no-transfer-progress clean integration-test failsafe:verify \
    -Dsurefire.options='-Duser.language=zh -Duser.country=CN -Xms1g -Xmx2g'
  ;;

test-ja)
  ./mvnw -e --no-transfer-progress clean integration-test failsafe:verify \
    -Dsurefire.options='-Duser.language=ja -Duser.country=JP -Xms1g -Xmx2g'
  ;;

test-pt)
  ./mvnw -e --no-transfer-progress clean integration-test failsafe:verify \
    -Dsurefire.options='-Duser.language=pt -Duser.country=PT -Xms1g -Xmx2g'
  ;;

test-tr)
  ./mvnw -e --no-transfer-progress clean integration-test failsafe:verify \
    -Dsurefire.options='-Duser.language=tr -Duser.country=TR -Xms1g -Xmx2g'
  ;;

test-ru)
  ./mvnw -e --no-transfer-progress clean integration-test failsafe:verify \
    -Dsurefire.options='-Duser.language=ru -Duser.country=RU -Xms1g -Xmx2g'
  ;;

test-al)
  ./mvnw -e --no-transfer-progress clean integration-test failsafe:verify \
    -Dsurefire.options='-Duser.language=sq -Duser.country=AL -Xms1g -Xmx2g'
  ;;

versions)
  ./mvnw -e --no-transfer-progress clean versions:dependency-updates-report \
    versions:plugin-updates-report
  DEP_UPDATES=$(get_outdated_dependencies "target/dependency-updates-report.xml")
  PLUGIN_UPDATES=$(get_outdated_plugins "target/plugin-updates-report.xml")
  if [ -n "${DEP_UPDATES}" ] || [ -n "${PLUGIN_UPDATES}" ]; then
    echo "New dependency versions:"
    echo "${DEP_UPDATES}"
    echo "New plugin versions:"
    echo "${PLUGIN_UPDATES}"
    echo "Verification is failed."
    false
  else
    echo "No new versions found"
  fi
  ;;

versions-on-pr)
  MASTER_WORKTREE=".ci-temp/versions-on-pr-master"
  mkdir -p .ci-temp

  echo "=== Running versions report on origin/master ==="
  git worktree add "$MASTER_WORKTREE" origin/master

  (
    cd "$MASTER_WORKTREE"
    ./mvnw -e --no-transfer-progress clean versions:dependency-updates-report \
      versions:plugin-updates-report
  )

  MASTER_DEPS=$(get_outdated_dependencies "$MASTER_WORKTREE/target/dependency-updates-report.xml")
  MASTER_PLUGINS=$(get_outdated_plugins "$MASTER_WORKTREE/target/plugin-updates-report.xml")

  git worktree remove --force "$MASTER_WORKTREE"

  echo "Master - outdated dependencies:"
  echo "${MASTER_DEPS:-(none)}"
  echo "Master - outdated plugins:"
  echo "${MASTER_PLUGINS:-(none)}"

  echo "=== Running versions report on PR branch ==="
  ./mvnw -e --no-transfer-progress clean versions:dependency-updates-report \
    versions:plugin-updates-report

  PR_DEPS=$(get_outdated_dependencies "target/dependency-updates-report.xml")
  PR_PLUGINS=$(get_outdated_plugins "target/plugin-updates-report.xml")

  echo "PR - outdated dependencies:"
  echo "${PR_DEPS:-(none)}"
  echo "PR - outdated plugins:"
  echo "${PR_PLUGINS:-(none)}"

  echo "=== Checking for NEW outdated dependencies introduced by PR ==="
  # comm -13: suppress lines only-in-master and common lines; keep only PR-only lines
  # i.e. deps that are outdated in PR but were NOT already outdated in master
  NEW_OUTDATED_DEPS=$(comm -13 \
    <(echo "${MASTER_DEPS}") \
    <(echo "${PR_DEPS}"))
  NEW_OUTDATED_PLUGINS=$(comm -13 \
    <(echo "${MASTER_PLUGINS}") \
    <(echo "${PR_PLUGINS}"))

  if [ -n "${NEW_OUTDATED_DEPS}" ] || [ -n "${NEW_OUTDATED_PLUGINS}" ]; then
    echo "FAILURE: PR introduces outdated dependencies not already present in master."
    if [ -n "${NEW_OUTDATED_DEPS}" ]; then
      echo "New outdated dependencies (please update to latest):"
      echo "${NEW_OUTDATED_DEPS}"
    fi
    if [ -n "${NEW_OUTDATED_PLUGINS}" ]; then
      echo "New outdated plugins (please update to latest):"
      echo "${NEW_OUTDATED_PLUGINS}"
    fi
    false
  else
    echo "SUCCESS: PR does not introduce any new outdated dependencies."
  fi
  ;;

markdownlint)
  mdl -g . && echo "All .md files verified"
  ;;

no-error-kafka)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo "CS_version: ${CS_POM_VERSION}"
  ./mvnw -e --no-transfer-progress clean install -Pno-validations
  echo "Checkout target sources ..."
  checkout_from "https://github.com/apache/kafka.git"
  cd .ci-temp/kafka/
  cat >> customConfig.gradle<< EOF
allprojects {
    repositories {
        mavenLocal()
    }
}
EOF
  ./gradlew checkstyleMain checkstyleTest \
    -I customConfig.gradle \
    -PcheckstyleVersion="${CS_POM_VERSION}"
  cd ..
  removeFolderWithProtectedFiles kafka
  ;;

no-error-pmd)
  export MAVEN_OPTS="-XX:MaxRAMPercentage=90"
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo "CS_version: ${CS_POM_VERSION}"
  ./mvnw -e --no-transfer-progress clean install -Pno-validations
  echo "Checkout target sources ..."
  checkout_from "https://github.com/pmd/build-tools.git"
  cd .ci-temp/build-tools/
  PMD_BUILD_TOOLS_VERSION="$(mvn -e --no-transfer-progress -q help:evaluate \
    -Dexpression=project.version -DforceStdout)"
  mvn -e --no-transfer-progress install
  cd ..
  git clone https://github.com/pmd/pmd.git
  cd pmd
  mvn -e --no-transfer-progress verify --show-version --errors --batch-mode \
                -DskipTests \
                -Dmaven.javadoc.skip=true \
                -Dmaven.source.skip=true \
                -Dpmd.skip=true \
                -Dcpd.skip=true \
                -Djapicmp.skip=true \
                -Dcyclonedx.skip=true \
                -Ddokka.skip=true \
                -Dcheckstyle.skip=false \
                -Dpmd.build-tools.version="${PMD_BUILD_TOOLS_VERSION}" \
                -Dcheckstyle.version="${CS_POM_VERSION}"
  cd ..
  removeFolderWithProtectedFiles build-tools
  removeFolderWithProtectedFiles pmd
  ;;

no-error-hazelcast)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo "CS_version: ${CS_POM_VERSION}"
  ./mvnw -e --no-transfer-progress clean package -Passembly,no-validations
  echo "Checkout Hazelcast sources..."
  checkout_from "https://github.com/hazelcast/hazelcast.git"
  cd .ci-temp/hazelcast

  # Modules using Apache License header
  APACHE_SOURCES=()
  for module in hazelcast hazelcast-spring hazelcast-spring-boot-autoconfiguration \
                hazelcast-spring-tests hazelcast-build-utils hazelcast-tpc-engine \
                hazelcast-archunit-rules; do
    if [ -d "$module/src/main/java" ]; then
      APACHE_SOURCES+=("$module/src/main/java")
    fi
    if [ -d "$module/src/test/java" ]; then
      APACHE_SOURCES+=("$module/src/test/java")
    fi
  done

  cat > checkstyle-apache.properties << EOF
checkstyle.suppressions.file=checkstyle/suppressions.xml
checkstyle.header.file=checkstyle/ClassHeaderApache.txt
EOF
  echo "Running Checkstyle on Apache-licensed modules..."
  readarray -t apache_files < <(find "${APACHE_SOURCES[@]}" \
    -name '*.java' ! -name 'module-info.java')
  java -Xmx3g -jar ../../target/checkstyle-"${CS_POM_VERSION}"-all.jar \
    -c checkstyle/checkstyle.xml \
    -p checkstyle-apache.properties \
    "${apache_files[@]}"

  # hazelcast-sql uses Hazelcast Community License header
  COMMUNITY_SOURCES=()
  if [ -d "hazelcast-sql/src/main/java" ]; then
    COMMUNITY_SOURCES+=("hazelcast-sql/src/main/java")
  fi
  if [ -d "hazelcast-sql/src/test/java" ]; then
    COMMUNITY_SOURCES+=("hazelcast-sql/src/test/java")
  fi

  cat > checkstyle-community.properties << EOF
checkstyle.suppressions.file=checkstyle/suppressions.xml
checkstyle.header.file=checkstyle/ClassHeaderHazelcastCommunity.txt
EOF
  echo "Running Checkstyle on Community-licensed modules (hazelcast-sql)..."
  readarray -t community_files < <(find "${COMMUNITY_SOURCES[@]}" \
    -name '*.java' ! -name 'module-info.java')
  java -Xmx3g -jar ../../target/checkstyle-"${CS_POM_VERSION}"-all.jar \
    -c checkstyle/checkstyle.xml \
    -p checkstyle-community.properties \
    "${community_files[@]}"

  cd ..
  removeFolderWithProtectedFiles hazelcast
  ;;

no-error-xwiki)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  ANTLR4_VERSION="$(getMavenProperty 'antlr4.version')"
  echo "version:${CS_POM_VERSION} antlr4:${ANTLR4_VERSION}"
  ./mvnw -e --no-transfer-progress clean install -Pno-validations
  echo "Checkout target sources ..."
  checkout_from "https://github.com/xwiki/xwiki-commons.git"
  cd .ci-temp/xwiki-commons
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
  mvn -e --no-transfer-progress \
    -f xwiki-commons-tools/xwiki-commons-tool-webjar-node-handlers/pom.xml \
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
  checkout_from https://github.com/xwiki/xwiki-platform.git
  cd .ci-temp/xwiki-platform
  # Validate xwiki-platform
  mvn -e --no-transfer-progress checkstyle:check@default -Dcheckstyle.version="${CS_POM_VERSION}"
  cd ..
  removeFolderWithProtectedFiles xwiki-platform
  ;;

no-error-test-sbe)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo version:"$CS_POM_VERSION"
  ./mvnw -e --no-transfer-progress clean install -Pno-validations
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
  checkForVariable "GITHUB_TOKEN"
  checkForVariable "PR_NUMBER"

  mkdir -p .ci-temp/verify-no-exception-configs
  working_dir=.ci-temp/verify-no-exception-configs
  curl -s --fail-with-body -o "$working_dir/checks-nonjavadoc-error.xml" \
    -H "Authorization: token $GITHUB_TOKEN" \
    https://raw.githubusercontent.com/checkstyle/contribution/master/checkstyle-tester/checks-nonjavadoc-error.xml
  curl -s --fail-with-body -o "$working_dir/checks-only-javadoc-error.xml" \
    -H "Authorization: token $GITHUB_TOKEN" \
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
    if [[ $PR_NUMBER =~ ^([0-9]+)$ ]]; then
      LINK_PR=https://api.github.com/repos/checkstyle/checkstyle/pulls/$PR_NUMBER
      REGEXP="https://github.com/checkstyle/contribution/pull/[0-9]+"
      CONTRIBUTION_PR_LINK=$(curl -s -H "Authorization: token $GITHUB_TOKEN" "$LINK_PR" \
                  | jq -r '.body' | grep -Eo "$REGEXP" | head -1 | cat )
      echo "Link to contribution PR: ${CONTRIBUTION_PR_LINK}"
      if [[ -z $CONTRIBUTION_PR_LINK ]]; then
        echo 'You introduce new Check'
        diff -u $working_dir/web.txt $working_dir/file.txt | cat
        echo 'Please create PR to repository https://github.com/checkstyle/contribution'
        echo 'and add your new Check '
        echo '   to file checkstyle-tester/checks-nonjavadoc-error.xml'
        echo 'or to file checkstyle-tester/checks-only-javadoc-error.xml'
        echo 'Place the contribution repository PR link in the description of this PR.'
        echo 'PR for contribution repository will be merged right after this PR.'
        fail=1;
      else
        CONTRIBUTION_PR_NUMBER="$(echo "$CONTRIBUTION_PR_LINK" | grep -oP '(?<=pull/)[0-9]+')"
        LINK_FILES="https://api.github.com/repos/checkstyle/contribution/pulls/$CONTRIBUTION_PR_NUMBER/files"
        FILES="$(curl -s -H "Authorization: token $GITHUB_TOKEN" "$LINK_FILES" | jq -r '
          .[]
          | select(.filename == "checkstyle-tester/checks-only-javadoc-error.xml"
                or .filename == "checkstyle-tester/checks-nonjavadoc-error.xml")
          | { filename, patch }
        ')"
        FILE_COUNT="$(echo "$FILES" | jq -s 'length')"
        if [ "$FILE_COUNT" -ne 1 ]; then
          echo "Expected only 1 of checkstyle-tester/checks-only-javadoc-error.xml or"
          echo "checkstyle-tester/checks-nonjavadoc-error.xml to be changed in"
          echo "$CONTRIBUTION_PR_LINK. Found $FILE_COUNT files changed."
          fail=1;
        else
          PATCH="$(echo "$FILES" | jq -r '.patch')"
          MODULE_NAME="$(echo "$DIFF_TEXT" | grep -E "^[+-][^+-]" | sed 's/^.//')"
          if echo "$PATCH" | grep -qE "\+\s*<module name=\"$MODULE_NAME\""; then
            echo "Module $MODULE_NAME found in contribution PR patch."
          else
            echo "Module $MODULE_NAME not found in contribution PR patch."
            echo "Please add $MODULE_NAME to only one of the following files:"
            echo '   checkstyle-tester/checks-nonjavadoc-error.xml'
            echo 'or checkstyle-tester/checks-only-javadoc-error.xml'
            fail=1;
          fi
        fi
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
  export MAVEN_OPTS='-Xmx2g'
  ./mvnw -e --no-transfer-progress clean verify -DskipTests -DskipITs \
    -Dpmd.skip=true -Dspotbugs.skip=true -Djacoco.skip=true
  ;;

spotbugs-and-pmd)
  mkdir -p .ci-temp/spotbugs-and-pmd
  CHECKSTYLE_DIR=$(pwd)
  export MAVEN_OPTS="-Xmx4g"
  ./mvnw -e --no-transfer-progress clean pmd:check
  ./mvnw -e --no-transfer-progress clean test-compile spotbugs:check
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
  ./mvnw -e --no-transfer-progress clean site -Pno-validations
  ;;

release-dry-run)
  if [ "$(git log -1 | grep -E "\[maven-release-plugin\] prepare release" | cat | wc -l)" -lt 1 ]
  then
    ./mvnw -e --no-transfer-progress release:prepare -DdryRun=true --batch-mode \
    -Darguments='-DskipTests -DskipITs -Djacoco.skip=true -Dpmd.skip=true \
      -Dspotbugs.skip=true -Dxml.skip=true -Dcheckstyle.ant.skip=true \
      -Dcheckstyle.skip=true -Dgpg.skip=true --no-transfer-progress'
    ./mvnw -e --no-transfer-progress release:clean
  fi
  ;;

assembly-run-all-jar)
  ./mvnw -e --no-transfer-progress clean package -Passembly,no-validations
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo version:"$CS_POM_VERSION"
  mkdir -p .ci-temp
  FOLDER=src/it/resources/com/google/checkstyle/test/chapter7javadoc/rule73wherejavadocrequired
  FILE=InputMissingJavadocTypeCorrect.java
  echo "Execution with plain text report"
  java -jar target/checkstyle-"$CS_POM_VERSION"-all.jar -c /google_checks.xml \
        $FOLDER/$FILE > .ci-temp/output.log
  fail=0
  if grep -vE '(Starting audit)|(warning)|(Audit done.)' .ci-temp/output.log ; then
    fail=1;
    exit $fail;
  elif grep 'warning' .ci-temp/output.log ; then
    fail=1;
    exit $fail;
  fi
  rm .ci-temp/output.log
  echo "Execution with xml report"
  java -jar target/checkstyle-"$CS_POM_VERSION"-all.jar -f xml -c /google_checks.xml \
        $FOLDER/$FILE -o .ci-temp/output.xml
  fail=0
  echo "Content of report:"
  cat .ci-temp/output.xml
  echo "Validation of report"
  if ! grep '</checkstyle>' .ci-temp/output.xml ; then
    fail=1;
    echo "no closed tag"
    exit $fail;
  elif ! grep 'file name' .ci-temp/output.xml ; then
    fail=1;
    echo "no file tag"
    exit $fail;
  fi
  rm .ci-temp/output.xml
  echo "Execution with sarif report"
  java -jar target/checkstyle-"$CS_POM_VERSION"-all.jar -f sarif -c /google_checks.xml \
        $FOLDER/$FILE -o .ci-temp/output.json
  fail=0
  echo "Content of report:"
  cat .ci-temp/output.json
  echo "Validation of report"
  if ! grep 'downloadUri' .ci-temp/output.json ; then
    fail=1;
    exit $fail;
  elif ! grep 'results' .ci-temp/output.json ; then
    fail=1;
    exit $fail;
  fi
  rm .ci-temp/output.json

  ;;

check-since-version)
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

compile-test-resources)
  # this task is useful during migration to new JDK to let compile resources on new jdk only
  ./mvnw -e --no-transfer-progress clean test-compile \
  -Dcheckstyle.skipCompileInputResources=false \
  -Dmaven.compiler.release=21
  ;;

javac21-exceptional)
  # InputPackageDeclarationEmptyFile - empty file, no ability to put explanation comment
  # beforeexecutionexclusionfilefilter - exceptional hack for examples
  files=($(grep -RELi --include='*.java' \
        --exclude='module-info.java' \
        --exclude='InputPackageDeclarationEmptyFile.java' \
        --exclude-dir="beforeexecutionexclusionfilefilter" \
        '// non-compiled (syntax|with javac)?\:' \
        src/test/resources-noncompilable \
        src/it/resources-noncompilable \
        src/xdocs-examples/resources-noncompilable))
  mkdir -p target
  for file in "${files[@]}"
  do
    echo ""
    echo "Compiling ${file} with standard JDK21"
    echo "Reason: " "$(grep "non-compiled" "${file}")"
    javac -d target "${file}"
  done

  files=($(grep -Rli --include='*.java' ': No package statement for testing purposes.' \
        src/test/resources-noncompilable \
        src/it/resources-noncompilable \
        src/xdocs-examples/resources-noncompilable || true))
  if [[  ${#files[@]} -eq 0 ]]; then
    echo "No Java files to process"
  else
    mkdir -p target
    for file in "${files[@]}"
    do
      echo ""
      echo "Compiling ${file} with standard JDK21"
      echo "Reason: " "$(grep "non-compiled" "${file}")"
      javac -d target "${file}"
    done
  fi
  ;;

javac25)
  files=($(grep -Rli --include='*.java' ': Compilable with Java25' \
        src/test/resources-noncompilable \
        src/it/resources-noncompilable \
        src/xdocs-examples/resources-noncompilable || true))
  if [[  ${#files[@]} -eq 0 ]]; then
    echo "No Java25 files to process"
  else
    mkdir -p target
    for file in "${files[@]}"
    do
      javac --release 25 -d target "${file}"
    done
  fi
  ;;

javadoc-tool-validate)
  output_dir=.ci-temp/javadoc
  classpath_file=.ci-temp/javadoc-test-classpath.txt
  # until https://github.com/checkstyle/checkstyle/issues/20675
  source ./.ci/javadoc-tool-excluded-packages.sh
  mkdir -p "$output_dir"

  ./mvnw -e --no-transfer-progress -q -Djacoco.skip=true -DskipTests clean test-compile
  ./mvnw -e --no-transfer-progress -q dependency:build-classpath \
    -Dmdep.outputFile="$classpath_file"
  dependency_classpath=$(<"$classpath_file")
  project_classpath="${dependency_classpath}:target/classes::target/test-classes"
  project_classpath="${project_classpath}:target/generated-classes"

  javadoc_source_version=$(java -version 2>&1 \
    | sed -n 's/.* version "\([0-9][0-9]*\).*/\1/p' \
    | head -n 1)
  javadoc_preview_args=(--source "$javadoc_source_version")

  custom_tags=(
    -tag 'apiNote:a:API Note:'
    -tag 'customTag:a:Custom tag:'
    -tag 'doubletag:a:Double tag:'
    -tag 'emptytag:a:Empty tag:'
    -tag 'implNote:a:Implementation Note:'
    -tag 'incomplete:a:Incomplete:'
    -tag 'mytag:a:Custom tag:'
    -tag 'todo:a:Todo:'
    -tag 'unknownTag:a:Unknown tag:'
  )

  validate_javadoc_packages() {
    local description=$1
    local source_root=$2
    local subpackages=$3
    local output_name=$4
    local log_file="$output_dir/$output_name.log"
    local root
    local package_name
    local excluded_packages=()

    for excluded_package in "${JAVADOC_TOOL_EXCLUDED_PACKAGES[@]}"
    do
      read -r root package_name _ <<< "$excluded_package"
      if [[ -n $root && $root != \#* && $root == "$source_root" ]]; then
        excluded_packages+=(-exclude "$package_name")
      fi
    done

    echo "Validating Javadoc syntax in $description"
    if ! javadoc -quiet \
      "${javadoc_preview_args[@]}" \
      -sourcepath "$source_root" \
      -classpath "$project_classpath" \
      -Xdoclint:syntax \
      "${custom_tags[@]}" \
      "${excluded_packages[@]}" \
      -subpackages "$subpackages" \
      -d "$output_dir/$output_name" 2> "$log_file"
    then
      cat "$log_file"
      exit 1
    fi
  }

  validate_javadoc_java25_noncompilable_files() {
    local description=$1
    local source_root=$2
    local output_name=$3
    local log_file="$output_dir/$output_name.log"
    local files=()
    local file

    while IFS= read -r file
    do
      files+=("$file")
    done < <(grep -Rli --include='*.java' ': Compilable with Java25' "$source_root" \
      | sort || true)

    if [[ ${#files[@]} -eq 0 ]]; then
      echo "No Java25 noncompilable files to validate in $description"
      return
    fi

    echo "Validating Javadoc syntax in $description"
    if ! javadoc -quiet \
      "${javadoc_preview_args[@]}" \
      -classpath "$dependency_classpath" \
      -Xdoclint:syntax \
      "${custom_tags[@]}" \
      -d "$output_dir/$output_name" \
      "${files[@]}" 2> "$log_file"
    then
      cat "$log_file"
      exit 1
    fi
  }

  validate_javadoc_packages \
    "test resources" \
    src/test/resources \
    com \
    test-resources
  validate_javadoc_packages \
    "IT resources" \
    src/it/resources \
    com \
    it-resources
  validate_javadoc_packages \
    "Xdoc Javadoc examples" \
    src/xdocs-examples/resources \
    com.puppycrawl.tools.checkstyle.checks.javadoc \
    xdocs-examples

  if [[ $javadoc_source_version -ge 25 ]]; then
    validate_javadoc_java25_noncompilable_files \
      "Java25 test noncompilable resources" \
      src/test/resources-noncompilable \
      test-resources-noncompilable-java25
    validate_javadoc_java25_noncompilable_files \
      "Java25 IT noncompilable resources" \
      src/it/resources-noncompilable \
      it-resources-noncompilable-java25
    validate_javadoc_java25_noncompilable_files \
      "Java25 Xdoc noncompilable examples" \
      src/xdocs-examples/resources-noncompilable \
      xdocs-examples-noncompilable-java25
  else
    echo "Skipping Java25 noncompilable resources on JDK $javadoc_source_version"
  fi

  rm "$classpath_file"
  ;;

jdeprscan)
  ./mvnw -e --no-transfer-progress clean compile test-compile \
    dependency:build-classpath -Dmdep.outputFile=target/classpath.txt -Pno-validations
  mkdir -p .ci-temp
  jdeprscan --class-path "$(cat target/classpath.txt)" --release 25 \
        target/classes target/test-classes > .ci-temp/jdeprscan.log 2>&1 || true
  if grep -qvE '(^Directory)|(^$)' .ci-temp/jdeprscan.log ; then
    cat .ci-temp/jdeprscan.log
    echo "jdeprscan reported deprecated API usage or errors."
    exit 1
  fi
  rm .ci-temp/jdeprscan.log
  ;;

package-site)
  export MAVEN_OPTS="-Xmx5g"
  ./mvnw -e --no-transfer-progress package -Passembly,no-validations
  ./mvnw -e --no-transfer-progress site -Dlinkcheck.skip=true
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

  export MAVEN_OPTS='-Xmx2g'
  # until https://github.com/checkstyle/checkstyle/issues/11637
  # shellcheck disable=SC2086
  ./mvnw -e --no-transfer-progress -Pno-validations clean package sonar:sonar \
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
  ./mvnw -e --no-transfer-progress clean install -Pno-validations
  echo "Checkout target sources ..."
  checkout_from https://github.com/pgjdbc/pgjdbc.git
  cd .ci-temp/pgjdbc
  ./gradlew --no-parallel --no-daemon checkstyleAll \
            -PenableMavenLocal -Pcheckstyle.version="${CS_POM_VERSION}" \
            -Dorg.gradle.daemon=false \
            -Dorg.gradle.jvmargs="-Xmx2g -XX:MaxMetaspaceSize=512m -Xms1g"
  cd ../
  removeFolderWithProtectedFiles pgjdbc
  ;;

no-error-orekit)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: "${CS_POM_VERSION}"
  ./mvnw -e --no-transfer-progress clean package -Passembly,no-validations
  echo "Checkout target sources ..."
  checkout_from https://github.com/Hipparchus-Math/hipparchus.git
  cd .ci-temp/hipparchus
  # checkout to version that Orekit expects
  SHA_HIPPARCHUS="220b0288f2d5a1e479""32d22e88535d0e091c5f50"
  git fetch --depth 1 origin "$SHA_HIPPARCHUS"
  git checkout $SHA_HIPPARCHUS
  mvn -e --no-transfer-progress install -DskipTests
  cd -
  checkout_from https://github.com/CS-SI/Orekit.git \
    "c25721337d7ea92f76fc5883d84e5745a""af2786f"
  cd .ci-temp/Orekit
  echo "checkstyle.header.file=license-header.txt" > checkstyle.properties
  readarray -t files < <(find src/main/java -name "*.java")
  java -jar "../../target/checkstyle-${CS_POM_VERSION}-all.jar" \
    -c checkstyle.xml \
    -p checkstyle.properties \
    "${files[@]}"
  cd ..
  removeFolderWithProtectedFiles Orekit
  removeFolderWithProtectedFiles hipparchus
  ;;

no-error-hibernate-search)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: "${CS_POM_VERSION}"
  ./mvnw -e --no-transfer-progress clean install -Pno-validations
  echo "Checkout target sources ..."
  checkout_from https://github.com/hibernate/hibernate-search.git
  cd .ci-temp/hibernate-search
  ./mvnw --version
  java -version
  ./mvnw -e --no-transfer-progress clean install -pl build/config -am \
     -DskipTests=true -Dmaven.compiler.failOnWarning=false \
     -Dcheckstyle.skip=true -Dforbiddenapis.skip=true \
     -Denforcer.skip=true \
     -Dversion.com.puppycrawl.tools.checkstyle="${CS_POM_VERSION}"
  ./mvnw -e --no-transfer-progress -f build/config/pom.xml checkstyle:check \
     -Dversion.com.puppycrawl.tools.checkstyle="${CS_POM_VERSION}"
  cd ../
  removeFolderWithProtectedFiles hibernate-search
  ;;

no-error-checkstyles-sevntu)
  set -e
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: "${CS_POM_VERSION}"
  ./mvnw -e --no-transfer-progress clean install -Pno-validations
  ./mvnw -e --no-transfer-progress compile verify -Psevntu \
    -Dmaven.sevntu-checkstyle-check.checkstyle.version="${CS_POM_VERSION}" \
    -Dmaven.test.skip=true -Dpmd.skip=true -Dspotbugs.skip=true \
    -Djacoco.skip=true -Dforbiddenapis.skip=true -Dxml.skip=true
  ;;

no-error-sevntu-checks)
  set -e
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: "${CS_POM_VERSION}"
  ./mvnw -e --no-transfer-progress clean install -Pno-validations
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
  ./mvnw -e --no-transfer-progress clean install -Pno-validations
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
  ./mvnw -e --no-transfer-progress clean install -Pno-validations
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
  ./mvnw -e --no-transfer-progress clean package -Passembly,no-validations
  echo "Checkout target sources ..."
  checkout_from https://github.com/jqno/equalsverifier.git
  cd .ci-temp/equalsverifier
  readarray -t files < <(find . \( -path '*/src/main/java/*.java' \
    -o -path '*/src/test/java/*.java' \))
  java -jar "../../target/checkstyle-${CS_POM_VERSION}-all.jar" \
    -c build/checkstyle-config.xml \
    "${files[@]}"
  cd ../
  removeFolderWithProtectedFiles equalsverifier
  ;;

no-error-strata)
  set -e
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: "${CS_POM_VERSION}"
  ./mvnw -e --no-transfer-progress clean install -Pno-validations
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
  ./mvnw -e --no-transfer-progress clean install -Pno-validations
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
  ./mvnw -e --no-transfer-progress clean package -Passembly,no-validations
  echo "Checkout target sources ..."
  checkout_from https://github.com/HtmlUnit/htmlunit.git
  cd .ci-temp/htmlunit
  # HtmlUnit is incrementally resolving violations from its new SummaryJavadoc check.
  sed -i'' '/<module name="SummaryJavadoc">/,/<\/module>/ {
    s|<module name="SummaryJavadoc">|<!-- <module name="SummaryJavadoc">|
    s|</module>|</module> -->|
  }' checkstyle.xml
  echo "checkstyle.suppressions.file=checkstyle_suppressions.xml" > checkstyle.properties
  find src/main/java src/test/java -name "*.java" -print0 | \
    xargs -0 -n 200 java -jar "../../target/checkstyle-${CS_POM_VERSION}-all.jar" \
    -c checkstyle.xml \
    -p checkstyle.properties
  cd ../
  removeFolderWithProtectedFiles htmlunit
  ;;

no-error-spotbugs)
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo CS_version: "${CS_POM_VERSION}"
  ./mvnw -e --no-transfer-progress clean install -Pno-validations
  echo "Checkout target sources ..."
  checkout_from https://github.com/spotbugs/spotbugs
  cd .ci-temp/spotbugs
  sed -i'' "s/mavenCentral()/mavenLocal(); mavenCentral()/" build.gradle
  sed -i'' "s/toolVersion.*$/toolVersion '${CS_POM_VERSION}'/" gradle/checkstyle.gradle
  ./gradlew :eclipsePlugin-junit:checkstyleTest -Dcheckstyle.version="${CS_POM_VERSION}"
  cd ../
  removeFolderWithProtectedFiles spotbugs
  ;;

no-error-trino)
  echo "Building checkstyle..."
  ./mvnw -e --no-transfer-progress clean install -Pno-validations -DskipTests
  echo "Resolving Checkstyle version from pom.xml..."
  CS_POM_VERSION="$(getCheckstylePomVersion)"
  echo "CS_version: ${CS_POM_VERSION}"
  echo "Cloning Trino sources..."
  checkout_from https://github.com/trinodb/trino.git "f45e24a240b089a6499c9bc1a4193b3fa""ba798ef"
  cd .ci-temp/trino
  echo "Running Checkstyle ${CS_POM_VERSION} on Trino..."
  ./mvnw -e --no-transfer-progress checkstyle:check -Dcheckstyle.version="${CS_POM_VERSION}"
  cd ../
  echo "Cleaning up cloned Trino repo..."
  removeFolderWithProtectedFiles trino
  ;;

no-exception-struts)
  export MAVEN_OPTS="-XX:MaxRAMPercentage=90"
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
  export MAVEN_OPTS="-Xmx4g"
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
  export MAVEN_OPTS="-Xmx4g"
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
  export MAVEN_OPTS="-Xmx4g"
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
  export MAVEN_OPTS="-Xmx4g"
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
  export MAVEN_OPTS="-Xmx4g"
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
  export MAVEN_OPTS="-Xmx4g"
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
  export MAVEN_OPTS="-Xmx4g"
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
  export MAVEN_OPTS="-Xmx4g"
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
  export MAVEN_OPTS="-Xmx4g"
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
  export MAVEN_OPTS="-Xmx4g"
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
  export MAVEN_OPTS="-Xmx4g"
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

git-check-single-commit)
  # Check if there are multiple commits that should be squashed into one
  COMMIT_COUNT=$(git rev-list --count master.."$PR_HEAD_SHA")
  if [ "$COMMIT_COUNT" -gt 1 ]; then
    echo Multiple commits found in PR. Please squash them into a single commit.
    echo Commit count: "$COMMIT_COUNT"
    echo To learn how to clean up your commit history, visit:
    echo https://checkstyle.org/beginning_development.html#Starting_Development
    exit 1
  fi
  ;;

git-check-pull-number)
  PR_NUMBER=${CIRCLE_PULL_REQUEST##*/}
  echo "PR_NUMBER=${PR_NUMBER}"
  COMMITS="$(git log --format=format:%B master.."$PR_HEAD_SHA")"

  echo "$COMMITS" | while read -r COMMIT ; do
    if [[ $COMMIT =~ 'Pull #' ]]; then
      echo "COMMIT=${COMMIT}"
      PULL_MESSAGE_NUMBER=$(echo "$COMMIT" | cut -d'#' -f 2 | cut -d':' -f 1)
      echo "PULL_MESSAGE_NUMBER=${PULL_MESSAGE_NUMBER}"
      if [[ $PULL_MESSAGE_NUMBER != "$PR_NUMBER" ]]; then
        echo "Referenced PR and this PR number do not match."
        echo "Commit message should reference '$PR_NUMBER'"
        exit 1
      fi
    fi
  done
  ;;

jacoco)
  export MAVEN_OPTS='-Xmx2g'
  ./mvnw -e --no-transfer-progress clean test \
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
  ./mvnw -e --no-transfer-progress clean verify
  ;;

package-all-jar)
  ./mvnw -e --no-transfer-progress clean package -Passembly
  ;;

website-only)
  ./mvnw -e --no-transfer-progress clean site -Pno-validations
  ;;

pmd)
  ./mvnw -e --no-transfer-progress clean test-compile pmd:check
  ;;

spotbugs)
  ./mvnw -e --no-transfer-progress clean test-compile spotbugs:check
  ;;

checkstyle)
  ./mvnw -e --no-transfer-progress clean compile antrun:run@ant-phase-verify
  ;;

forbiddenapis)
  ./mvnw -e --no-transfer-progress \
    clean compile test-compile forbiddenapis:testCheck@forbiddenapis-test
  ;;

run-test)
  if [[ -z "$2" ]] ; then
    echo "Error: test class is not defined."
    echo "Example: ./mvnw -e --no-transfer-progress clean test" \
     "-Dtest=XdocsPagesTest,XdocsJavaDocsTest"
    echo "Example: ./mvnw -e --no-transfer-progress clean test -Dtest=CheckerTest#testDestroy"
    exit 1
  fi
  ./mvnw -e --no-transfer-progress clean test -Dtest="$2"
  ;;

sevntu)
  ./mvnw -e --no-transfer-progress compile antrun:run@ant-phase-verify-sevntu -Psevntu
  ;;

spotless)
  ./mvnw -e --no-transfer-progress spotless:check
  ;;

openrewrite-checkstyle-auto-fix)
  echo "Cloning and building OpenRewrite recipes..."
  PROJECT_ROOT="$(pwd)"
  export MAVEN_OPTS="-Xmx4g -Xms2g"

  mkdir -p .ci-temp && cd .ci-temp
  git clone https://github.com/checkstyle/checkstyle-openrewrite-recipes.git
  cd checkstyle-openrewrite-recipes
  ./mvnw -e --no-transfer-progress clean install -DskipTests

  cd "$PROJECT_ROOT"

  echo "Running Checkstyle validation to get report for openrewrite..."
  set +e
  ./mvnw -e --no-transfer-progress clean compile antrun:run@ant-phase-verify
  set -e
  echo "Running CheckstyleAutoFix recipes..."
  ./mvnw -e --no-transfer-progress rewrite:run \
    -Drewrite.recipeChangeLogLevel=INFO \
    -Drewrite.activeRecipes=org.checkstyle.CheckstyleAutoFix

  echo "Checking for uncommitted changes..."
  ./.ci/print-diff-as-patch.sh target/rewrite.patch

  rm -rf .ci-temp/checkstyle-openrewrite-recipes
  ;;

openrewrite-refaster-rules-1)
  echo "Cloning and building OpenRewrite recipes..."
  PROJECT_ROOT="$(pwd)"
  export MAVEN_OPTS="-Xmx4g -Xms2g"

  mkdir -p .ci-temp && cd .ci-temp
  git clone https://github.com/checkstyle/checkstyle-openrewrite-recipes.git
  cd checkstyle-openrewrite-recipes
  ./mvnw -e --no-transfer-progress clean install -DskipTests

  cd "$PROJECT_ROOT"

  echo "Running RefasterRules Part 1 recipes..."
  ./mvnw -e --no-transfer-progress rewrite:run \
    -Drewrite.recipeChangeLogLevel=INFO \
    -Drewrite.activeRecipes=org.checkstyle.RefasterRules1

  echo "Checking for uncommitted changes..."
  ./.ci/print-diff-as-patch.sh target/rewrite.patch

  rm -rf .ci-temp/checkstyle-openrewrite-recipes
  ;;

openrewrite-refaster-rules-2)
  echo "Cloning and building OpenRewrite recipes..."
  PROJECT_ROOT="$(pwd)"
  export MAVEN_OPTS="-Xmx4g -Xms2g"

  mkdir -p .ci-temp && cd .ci-temp
  git clone https://github.com/checkstyle/checkstyle-openrewrite-recipes.git
  cd checkstyle-openrewrite-recipes
  ./mvnw -e --no-transfer-progress clean install -DskipTests

  cd "$PROJECT_ROOT"

  echo "Running RefasterRules Part 2 recipes..."
  ./mvnw -e --no-transfer-progress rewrite:run \
    -Drewrite.recipeChangeLogLevel=INFO \
    -Drewrite.activeRecipes=org.checkstyle.RefasterRules2

  echo "Checking for uncommitted changes..."
  ./.ci/print-diff-as-patch.sh target/rewrite.patch

  rm -rf .ci-temp/checkstyle-openrewrite-recipes
  ;;

openrewrite-static-analysis)
  echo "Cloning and building OpenRewrite recipes..."
  PROJECT_ROOT="$(pwd)"
  export MAVEN_OPTS="-Xmx4g -Xms2g"

  mkdir -p .ci-temp && cd .ci-temp
  git clone https://github.com/checkstyle/checkstyle-openrewrite-recipes.git
  cd checkstyle-openrewrite-recipes
  ./mvnw -e --no-transfer-progress clean install -DskipTests

  cd "$PROJECT_ROOT"

  echo "Running StaticAnalysis recipes..."
  ./mvnw -e --no-transfer-progress rewrite:run \
    -Drewrite.recipeChangeLogLevel=INFO \
    -Drewrite.activeRecipes=org.checkstyle.StaticAnalysis

  echo "Checking for uncommitted changes..."
  ./.ci/print-diff-as-patch.sh target/rewrite.patch

  rm -rf .ci-temp/checkstyle-openrewrite-recipes
  ;;

*)
  echo "Unexpected argument: $1"
  echo "Supported tasks:"
  list_tasks "${0}"
  false
  ;;

esac

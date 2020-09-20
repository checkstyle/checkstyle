#!/bin/bash
set -e

removeFolderWithProtectedFiles() {
  find $1 -delete
}

case $1 in

assembly)
  mvn -e clean package -Passembly
  ;;

site)
  mvn -e clean site -Pno-validations -Dlinkcheck.skip=true
  ;;

verify)
  mvn -e clean verify
  ;;

test-de)
  mvn -e clean integration-test failsafe:verify \
    -DargLine='-Duser.language=de -Duser.country=DE -Xms1024m -Xmx2048m'
  ;;

test-es)
  mvn -e clean integration-test failsafe:verify \
    -DargLine='-Duser.language=es -Duser.country=ES -Xms1024m -Xmx2048m'
  ;;

test-fi)
  mvn -e clean integration-test failsafe:verify \
    -DargLine='-Duser.language=fi -Duser.country=FI -Xms1024m -Xmx2048m'
  ;;

test-fr)
  mvn -e clean integration-test failsafe:verify \
    -DargLine='-Duser.language=fr -Duser.country=FR -Xms1024m -Xmx2048m'
  ;;

test-ja)
  mvn -e clean integration-test failsafe:verify \
    -DargLine='-Duser.language=ja -Duser.country=JP -Xms1024m -Xmx2048m'
  ;;

test-pt)
  mvn -e clean integration-test failsafe:verify \
    -DargLine='-Duser.language=pt -Duser.country=PT -Xms1024m -Xmx2048m'
  ;;

test-tr)
  mvn -e clean integration-test failsafe:verify \
    -DargLine='-Duser.language=tr -Duser.country=TR -Xms1024m -Xmx2048m'
  ;;

test-zh)
  mvn -e clean integration-test failsafe:verify \
    -DargLine='-Duser.language=zh -Duser.country=CN -Xms1024m -Xmx2048m'
  ;;

spotbugs-and-pmd)
  mkdir -p .ci-temp/spotbugs-and-pmd
  CHECKSTYLE_DIR=$(pwd)
  mvn -e clean test-compile pmd:check spotbugs:check \
    -DargLine='-Xms1024m -Xmx2048m'
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

*)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac

#!/bin/bash
# Attention, there is no "-x" to avoid problems on CircleCI
set -e

function list_profiles() {
  POM_PATH="$(dirname "${0}")/../pom.xml"
  cat "$POM_PATH" | sed -E -n 's/^.*<id>(pitest-[^<]+)<\/id>.*$/\1/p' | sort
}

case $1 in

--list)
  echo "Supported profiles:"
  list_profiles "${0}"
  ;;

*)
  PROFILES=$(list_profiles "${0}");

  if [[ $(echo "$PROFILES" | grep -w -- "${1}" | cat) != "" ]]; then
    set +e
    mvn -e --no-transfer-progress -P"$1" clean test-compile org.pitest:pitest-maven:mutationCoverage
    EXIT_CODE=$?
    set -e
    groovy ./.ci/pitest-survival-check-xml.groovy "$1"
    exit $EXIT_CODE
  else
    echo "Unexpected argument: $*"
    echo "Usage $0 <profile>"
    echo "To see the full list of supported profiles run '$0 --list'"
    false
  fi
  ;;

esac

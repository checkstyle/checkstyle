#!/bin/bash
# Attention, there is no "-x" to avoid problems on CircleCI
set -e

function list_profiles() {
  POM_PATH="$(dirname "${0}")/../pom.xml"
  cat "$POM_PATH" | sed -n -e 's/^.*<id>\(pitest-[^<]\+\)<\/id>.*$/\1/p' | sort
}

case $1 in

--list)
  echo "Supported profiles:"
  list_profiles "${0}"
  ;;

*)
  PROFILES=$(list_profiles "${0}");

  if [[ $(echo "$PROFILES" | grep -w -- "${1}" | cat) != "" ]]; then
    mvn -e --no-transfer-progress -P"$1" clean test-compile org.pitest:pitest-maven:mutationCoverage
    if [[ "$2" != "" ]]; then
      groovy ./.ci/pitest-survival-check-xml.groovy "$1" "$2"
    else
      groovy ./.ci/pitest-survival-check-xml.groovy "$1"
    fi
  else
    echo "Unexpected argument: $*"
    echo "Usage $0 <profile> <extra argument for pitest-survival-check-xml.groovy>"
    echo "To see the full list of supported profiles run '$0 --list'"
    false
  fi
  ;;

esac

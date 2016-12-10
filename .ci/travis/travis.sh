#!/bin/bash

set -euo pipefail

case "$GOAL" in

verify)
  mvn verify
  ;;

all-sevntu-checks)
  grep " <module " config/checkstyle_sevntu_checks.xml | cut -d "\"" -f2 | grep -vE "Checker|TreeWalker|Filter|Holder" | sort | uniq | sed "s/Check$//" > file.txt
  wget -q http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/allclasses-frame.html -O - | html2text | grep -E "Check$" | cut -d " " -f6 | sort | uniq | sed "s/Check$//" > web.txt
  diff web.txt file.txt 
  ;;

*)
  echo "Unexpected GOAL mode: $GOAL"
  exit 1
  ;;

esac

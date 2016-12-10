#!/bin/bash

set -e

case "$GOAL" in

verify)
  mvn verify
  ;;

all-sevntu-checks)
  xmlstarlet sel --net --template -m .//module -v "@name" -n checkstyle_sevntu_checks.xml | grep -vE "Checker|TreeWalker|Filter|Holder" | sort | uniq | sed "s/Check$//" > file.txt
  wget -q http://sevntu-checkstyle.github.io/sevntu.checkstyle/apidocs/allclasses-frame.html -O - | html2text | grep -E "Check$" | cut -d " " -f6 | sort | uniq | sed "s/Check$//" > web.txt
  diff web.txt file.txt | cat
  ;;

*)
  echo "Unexpected GOAL mode: $GOAL"
  exit 1
  ;;

esac

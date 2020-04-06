#!/bin/bash
set -x

case $1 in

verify)
  mvn -e clean verify
  ;;

assembly)
  mvn -e clean package -Passembly
  ;;

site)
  mvn -e clean site -Pno-validations -Dlinkcheck.skip=true
  ;;

*)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac

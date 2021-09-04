#!/bin/bash
set -e

source ./.ci/util.sh

case $1 in

restore-maven-cache)
  curl -o cache.tar -SsL https://sourceforge.net/projects/checkstyle/files/drone-io/drone-io-m2-repository.tar/download
  tar -xf cache.tar -C /
  rm cache.tar
  ;;

*)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac

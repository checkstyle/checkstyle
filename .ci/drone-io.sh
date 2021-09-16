#!/bin/bash
set -e

source ./.ci/util.sh

case $1 in

restore-maven-cache)
  mkdir -p .ci-temp
  curl -o .ci-temp/cache.tar -SsL \
    https://sourceforge.net/projects/checkstyle/files/drone-io/drone-io-m2-repository.tar/download
  tar -xf .ci-temp/cache.tar -C /
  rm .ci-temp/cache.tar
  ;;

*)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac

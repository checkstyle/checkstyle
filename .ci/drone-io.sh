#!/bin/bash
set -e

source ./.ci/util.sh

case $1 in

restore-maven-cache)
  mkdir -p .ci-temp
  # public backup:
  # https://sourceforge.net/projects/checkstyle/files/drone-io/drone-io-m2-repository.tar/download
  curl --fail-with-body -o .ci-temp/cache.tar -SsL \
    https://www.dropbox.com/s/"sh1g8o4h16p4n""x6"/cache.tar?dl=0
  tar -xf .ci-temp/cache.tar -C /
  rm .ci-temp/cache.tar
  ;;

*)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac

#!/bin/bash
# Attention, there is no "-x" to avoid problems on Wercker
set -e

source ./.ci/util.sh

case $1 in

validate-ci-temp-empty)
  fail=0
  if [ -d ".ci-temp/" ]; then
    echo ".ci-temp/ exists"
    if [ -z "$(ls -A .ci-temp)" ]; then
      echo "Empty .ci-temp/ validation did not find any warnings."
    else
      echo "Directory .ci-temp/ is not empty. Verification failed."
      echo "Contents of .ci-temp/:"
      fail=1
    fi
    ls -A .ci-temp --color=auto
  fi
  exit $fail
  ;;

git-status)
  if [ "$(git status | grep 'Changes not staged\|Untracked files')" ]; then
    printf "Please clean up or update .gitattributes file.\nGit status output:\n"
    git status
    sleep 5s
    false
  fi
  ;;

*)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac

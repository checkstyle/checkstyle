#!/usr/bin/env bash

set -euo pipefail

source ./.ci/util.sh

case $1 in

generate-site)
  ./mvnw -e --no-transfer-progress clean site \
    -Pno-validations \
    -Dmaven.javadoc.skip=false \
    -Djdepend.skip=false
  ;;

publish-site)
  checkForVariable "AWS_BUCKET_NAME"
  checkForVariable "AWS_REGION"

  aws s3 sync "target/site/" "s3://${AWS_BUCKET_NAME}/website/latest/" --delete

  LINK="https://${AWS_BUCKET_NAME}.s3.${AWS_REGION}.amazonaws.com/website/latest/index.html"
  echo "Published to '$LINK'"
  ;;

*)
  echo "Unexpected argument: $1"
  exit 1
  ;;

esac

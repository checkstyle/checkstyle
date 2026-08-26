#!/bin/bash
set -e

source ./.ci/util.sh

case $1 in

# Gets PR information (branch, commit_sha) and saves to .ci-temp
get-pr-info)
  checkForVariable "GITHUB_TOKEN"
  checkForVariable "PR_NUMBER"
  mkdir -p .ci-temp
  
  URL="https://api.github.com/repos/checkstyle/checkstyle/pulls/${PR_NUMBER}"
  
  curl --fail-with-body -X GET "$URL" \
      -H "Accept: application/vnd.github+json" \
      -H "Authorization: token $GITHUB_TOKEN" \
      -o .ci-temp/info.json

  jq .head.ref .ci-temp/info.json > .ci-temp/branch
  jq .head.sha .ci-temp/info.json > .ci-temp/commit_sha
  
  BRANCH=$(xargs < .ci-temp/branch)
  COMMIT_SHA=$(xargs < .ci-temp/commit_sha | cut -c 1-7)
  
  ./.ci/append-to-github-output.sh "branch" "$BRANCH"
  ./.ci/append-to-github-output.sh "commit_sha" "$COMMIT_SHA"
  ;;

# Generates the site using Maven
generate-site)
  cd .ci-temp/checkstyle
  ../../mvnw -e --no-transfer-progress clean site -Pno-validations \
    -Dmaven.javadoc.skip=false -Djdepend.skip=false
  ;;

# Copies the site to AWS S3 bucket and generates the message
publish-site)
  checkForVariable "GITHUB_TOKEN"
  checkForVariable "COMMIT_SHA"
  checkForVariable "PR_NUMBER"
  checkForVariable "AWS_BUCKET_NAME"
  checkForVariable "AWS_REGION"
  
  TIME=$(date +%Y%m%d%H%M%S)
  FOLDER="${COMMIT_SHA}_$TIME"
  SITE=".ci-temp/checkstyle/target/site"
  LINK="https://${AWS_BUCKET_NAME}.s3.${AWS_REGION}.amazonaws.com"
  
  aws s3 cp "$SITE" "s3://${AWS_BUCKET_NAME}/$FOLDER/" --recursive --quiet
  echo "$LINK/$FOLDER/index.html" > .ci-temp/message
  
  ./.ci/generate-extra-site-links.sh "$PR_NUMBER" "$LINK/$FOLDER"
  
  ./.ci/append-to-github-output.sh "message" "$(cat .ci-temp/message)"
  ;;

*)
  echo "Unexpected argument: $1"
  sleep 5s
  false
  ;;

esac

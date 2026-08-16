#!/bin/bash
set -e

source ./.ci/util.sh

mkdir -p .ci-temp

if [ -n "$MSG" ]; then
  echo "$MSG" > .ci-temp/message
  exit 0
fi

checkForVariable "GITHUB_TOKEN"
checkForVariable "GITHUB_RUN_ID"

JOBS_LINK="https://github.com/checkstyle/checkstyle/actions/runs/${GITHUB_RUN_ID}"
API_LINK="https://api.github.com/repos/checkstyle/checkstyle/actions/runs/${GITHUB_RUN_ID}/jobs"

curl --fail-with-body -X GET "${API_LINK}" \
  -H "Accept: application/vnd.github+json" \
  -H "Authorization: token $GITHUB_TOKEN" \
  -o .ci-temp/info.json

jq '.jobs' .ci-temp/info.json > .ci-temp/jobs
jq -r '.[] | select(any(.steps[]; .conclusion == "failure")) | .name' \
  .ci-temp/jobs > .ci-temp/job_name
jq '.[] | select(any(.steps[]; .conclusion == "failure")) | .steps' .ci-temp/jobs > .ci-temp/steps
jq -r '.[] | select(.conclusion == "failure") | .name' .ci-temp/steps > .ci-temp/step_name

if [ -n "$FAILURE_PREFIX" ]; then
  echo "${FAILURE_PREFIX} failed on phase $(cat .ci-temp/job_name)," > .ci-temp/message
else
  echo "Job failed on phase $(cat .ci-temp/job_name)," > .ci-temp/message
fi
echo "step $(cat .ci-temp/step_name).<br>Link: $JOBS_LINK" >> .ci-temp/message

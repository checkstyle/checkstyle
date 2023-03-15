#!/usr/bin/env sh
set -e
# inspired by https://github.com/viesure/blog-sonar-build-breaker

SONAR_RESULT="target/sonar/report-task.txt"
SONAR_SERVER="https://sonarcloud.io"

if [ -z "$SONAR_API_TOKEN" ]; then
  echo "Sonar API Token not set"
  exit 1
fi

if [ ! -f $SONAR_RESULT ]; then
  echo "Sonar result does not exist"
  exit 1
fi

CE_TASK_ID=$(cat $SONAR_RESULT | sed -n 's/ceTaskId=\(.*\)/\1/p')

if [ -z "$CE_TASK_ID" ]; then
  echo "ceTaskId is not set from sonar build"
  exit 1
fi

TASK_URL="$SONAR_SERVER/api/ce/task?id=$CE_TASK_ID"
HTTP_STATUS=$(curl -s -o /dev/null -w '%{http_code}' \
    -u "$SONAR_API_TOKEN": "$TASK_URL")

if [  "$HTTP_STATUS" -ne 200 ]; then
  echo "Sonar API Token has no access rights"
  exit 1
fi

# While report is processed ANALYSIS_ID is not available and jq returns null
ANALYSIS_ID=$(curl -X GET -s -u "$SONAR_API_TOKEN": "$TASK_URL" \
    | jq -r .task.analysisId)
I=1
TIMEOUT=0
while [ "$ANALYSIS_ID" = "null" ]; do
  if [ "$TIMEOUT" -gt 30 ]; then
    echo "Timeout of " + $TIMEOUT + " seconds exceeded for getting ANALYSIS_ID"
    exit 1
  fi
  sleep $I
  TIMEOUT=$((TIMEOUT+I))
  I=$((I+1))
  ANALYSIS_ID=$(curl -X GET -s -u "$SONAR_API_TOKEN": "$TASK_URL" \
      | jq -r .task.analysisId)
done

ANALYSIS_URL="$SONAR_SERVER/api/qualitygates/project_status?analysisId=$ANALYSIS_ID"
STATUS=$(curl -X GET -s -u "$SONAR_API_TOKEN": "$ANALYSIS_URL" \
    | jq -r .projectStatus.status)

DASHBOARD_URL=$(cat $SONAR_RESULT | sed -n 's/dashboardUrl=\(.*\)/\1/p')

if [ "$STATUS" = "ERROR" ]; then
  echo "Quality gate failed."
  echo "See sonar dashboard at '${DASHBOARD_URL}' for failures."
  exit 1
fi

echo "Sonar Quality gate is OK"
exit 0

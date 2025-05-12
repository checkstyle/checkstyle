#!/bin/bash

# Script to run checkstyle after code formatting

set -e  # Exit immediately if a command exits with a non-zero status

echo "Running Checkstyle verification..."

# Run checkstyle with the specified configuration

PROJECT_DIR=$(pwd)

mvn -e --no-transfer-progress checkstyle:check \
  -Dcheckstyle.config.location="$PROJECT_DIR/config/checkstyle-checks.xml" \
  -Dcheckstyle.cache.file="$PROJECT_DIR/target/cachefile" \
  -Dcheckstyle.header.file="$PROJECT_DIR/config/java.header" \
  -Dcheckstyle.regexp.header.file="$PROJECT_DIR/config/java-regexp.header" \
  -Dcheckstyle.importcontrol.file="$PROJECT_DIR/config/import-control.xml" \
  -Dcheckstyle.importcontroltest.file="$PROJECT_DIR/config/import-control-test.xml" \
  -Dcheckstyle.suppressions.file="$PROJECT_DIR/config/suppressions.xml" \
  -Dcheckstyle.suppressions-xpath.file="$PROJECT_DIR/config/suppressions-xpath.xml" \
  -Dcheckstyle.java.version="${JAVA_VERSION:-11}" \
  || CHECKSTYLE_FAILED=true

# Check if checkstyle found violations
if [ "$CHECKSTYLE_FAILED" = true ]; then
  # Store the checkstyle results in a directory that will be available as artifacts
  mkdir -p target/checkstyle-results
  cp target/checkstyle-result.xml target/checkstyle-results/ 2>/dev/null || true

  # Display a helpful error message with link to artifacts
  echo ""
  echo "ATTENTION GITHUB CONTRIBUTOR:"
  echo "There are inspection problems."
  echo "Review results in 'ARTIFACTS' tab in the CircleCI UI above, or follow the link below:"

  CIRCLE_URL="https://app.circleci.com/pipelines/github/checkstyle/checkstyle"
  # It seems like the value of this number does not matter when navigating to the artifacts tab,
  # but it is required to be present in the URL.
  SOME_NUMBER=1
  echo "$CIRCLE_URL/$SOME_NUMBER/workflows/$CIRCLE_WORKFLOW_ID/jobs/$CIRCLE_BUILD_NUM/artifacts"
  exit 1
else
  echo "Checkstyle verification passed successfully."
  exit 0
fi

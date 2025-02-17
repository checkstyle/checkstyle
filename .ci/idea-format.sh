#!/bin/bash -e

#################################################
# IntelliJ IDEA code formatting for checkstyle.
#
# Example Mac OS:
# IDEA_PATH="/Applications/IntelliJ IDEA.app/Contents/MacOS/idea" ./.ci/format.sh
#
# Example Linux:
# export IDEA_PATH=$HOME/java/idea-IU-172.4574.11 && ./.ci/format.sh
#################################################

PROJECT_DIR=$PWD/
FORMAT_CONFIG_PATH=$PWD/config/intellij-idea-format.xml

# Check IDEA_PATH env variable
if [[ -z $IDEA_PATH ]]; then
    echo "IDEA_PATH variable not found."
    # Try to search in path
    IDEA_PATH="$(which idea)"
    if [ -z "$IDEA_PATH" ]; then
        echo "IntelliJ IDEA was not found in path."
        exit -1
    fi
fi

# Execute compilation of Checkstyle to generate all source files
# YOU MUST BUILD PROJECT BEFORE FORMAT EXECUTION!!!
mvn -e --no-transfer-progress clean compile

echo ""
for i in {1..100}; do echo -n "#"; done
echo ""

echo "Intellij Idea code formatting is about to start"
echo "Progress output will be flushed at end. Formatting is in progress ..."

# we pipe standard error to /dev/null to avoid messy IDEA output in console
IDEA_OUTPUT=$("$IDEA_PATH"/bin/format.sh -settings "$FORMAT_CONFIG_PATH" "$PROJECT_DIR" 2>&1)
echo "$IDEA_OUTPUT"

if [[ $IDEA_OUTPUT == "Already running" ]]; then
    echo "It might be that Intellij Idea is running, please close it."
    exit 1;
fi

echo "Checking results ..."
MODIFIED_FILES=$(find . -type f -name "*.java" -exec git diff --name-only {} \; | wc -l)

if [[ $MODIFIED_FILES -gt 0 ]] && [[ "$CIRCLECI" == "true" ]]; then
    echo ""
    echo "ATTENTION GITHUB CONTRIBUTOR:"
    echo "Some files were modified during formatting."
    echo "Review results in 'ARTIFACTS' tab in the CircleCI UI above, or follow the link below:"

    CIRCLE_URL="https://app.circleci.com/pipelines/github/checkstyle/checkstyle"
    # It seems like the value of this number does not matter when navigating to the artifacts tab,
    # but it is required to be present in the URL.
    SOME_NUMBER=1
    echo "$CIRCLE_URL/$SOME_NUMBER/workflows/$CIRCLE_WORKFLOW_ID/jobs/$CIRCLE_BUILD_NUM/artifacts"
    exit 1;
elif [[ $MODIFIED_FILES -gt 0 ]]; then
    echo "Some files were modified during formatting. Modified files:"
    git diff --name-only
    exit 1;
else
    echo "Formatting did not modify any files - code is already properly formatted"
fi

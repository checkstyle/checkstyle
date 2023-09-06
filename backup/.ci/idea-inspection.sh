#!/bin/bash -e

#################################################
# IntelliJ IDEA inspections for checkstyle.
#
# Example Mac OS:
# IDEA_PATH="/Applications/IntelliJ IDEA.app/Contents/MacOS/idea" ./.ci/idea-inspection.sh
#
# Example Linux:
# export IDEA_PATH=$HOME/java/idea-IU-172.4574.11 && ./.ci/idea-inspection.sh
#################################################

PROJECT_DIR=$PWD/
INSPECTIONS_PATH=$PWD/config/intellij-idea-inspections.xml
RESULTS_DIR=$PWD/target/inspection-results
NOISE_LVL=v1
# we need to export this variable as it is required for idea.sh script
export IDEA_PROPERTIES=$PWD/config/intellij-idea-inspections.properties

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
# YOU MUST BUILD PROJECT BEFORE INSPECTION EXECUTION!!!
mvn -e --no-transfer-progress clean compile

echo ""
for i in {1..100}; do echo -n "#"; done
echo ""

mkdir -p "$RESULTS_DIR"
rm -rf "$RESULTS_DIR"/*

echo "Intellij Idea validation is about to start"
echo "Progress output will be flushed at end. Validation is in progress ..."

# we pipe standard error to /dev/null to avoid messy IDEA output in console
IDEA_OUTPUT=$("$IDEA_PATH"/bin/inspect.sh "$PROJECT_DIR" "$INSPECTIONS_PATH" "$RESULTS_DIR" \
   -$NOISE_LVL 2>/dev/null)

if [[ $IDEA_OUTPUT == "Already running" ]]; then
    echo "It might be that Intellij Idea is running, please close it."
    exit 1;
fi

echo "Checking results ..."
PROBLEM_COUNT=$(grep -R "<problems" "$RESULTS_DIR"/ | cat | wc -l )

if [[ $PROBLEM_COUNT -gt 0 ]] && [[ "$CIRCLECI" == "true" ]]; then
    echo ""
    echo "ATTENTION GITHUB CONTRIBUTOR:"
    echo "There are inspection problems."
    echo "Review results in 'ARTIFACTS' tab in the CircleCI UI above, or follow the link below:"

    CIRCLE_URL="https://app.circleci.com/pipelines/github/checkstyle/checkstyle"
    # It seems like the value of this number does not matter when navigating to the artifacts tab,
    # but it is required to be present in the URL.
    SOME_NUMBER=1
    echo "$CIRCLE_URL/$SOME_NUMBER/workflows/$CIRCLE_WORKFLOW_ID/jobs/$CIRCLE_BUILD_NUM/artifacts"
    exit 1;
elif [[ $PROBLEM_COUNT -gt 0 ]]; then
    echo "There are inspection problems. Review results at $RESULTS_DIR folder. Files:"
    grep -Rl "<problems" "$RESULTS_DIR"/
    exit 1;
else
    echo "Inspection did not find any problems"
fi

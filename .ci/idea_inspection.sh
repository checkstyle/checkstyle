#!/bin/bash -e

#################################################
# IntelliJ IDEA inspections for checkstyle.
#
# Example Mac OS:
# IDEA_PATH="/Applications/IntelliJ IDEA.app/Contents/MacOS/idea" ./.ci/idea_inspection.sh
#
# Example Linux:
# export IDEA_PATH=$HOME/java/idea-IU-172.4574.11 && ./.ci/idea_inspection.sh
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
    if [ -z $IDEA_PATH ]; then
        echo "IntelliJ IDEA was not found in path."
        exit -1
    fi
fi

# Execute compilation of Checkstyle to generate all source files
mvn -e --no-transfer-progress clean compile

mkdir -p $RESULTS_DIR
rm -rf $RESULTS_DIR/*

echo "Intellij Idea validation is about to start"
echo "Progress output will be flushed at end. Validation is in progress ..."
IDEA_OUTPUT=`$IDEA_PATH/bin/inspect.sh $PROJECT_DIR $INSPECTIONS_PATH $RESULTS_DIR -$NOISE_LVL`

if [[ $IDEA_OUTPUT == "Already running" ]]; then
    echo "It might be that Intellij Idea is running, please close it."
    exit 1;
fi

echo "Checking results ..."
if [[ $(grep -R "<problems" $RESULTS_DIR/ | cat | wc -l ) > 0 ]]; then
    echo "There are inspection problems. Review results at $RESULTS_DIR folder. Files:"
    grep -Rl "<problems" $RESULTS_DIR/
    exit 1;
else
    echo "Inspection did not found any problems"
fi

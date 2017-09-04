#!/bin/bash -e

#################################################
# IntelliJ IDEA inspections for checkstyle.
#
# Example Mac OS:
# export IDEA_PATH="/Applications/IntelliJ IDEA.app/Contents/MacOS/idea"
# bash ./.ci/idea_inspection.bash
# 
# Example Linux:
# export IDEA_PATH=/opt/idea-IC-171.4694.70/bin/idea.sh
# export IDEA_PROPERTIES=$PWD/config/idea.properties
# bash ./.ci/idea_inspection.bash
#################################################

PROJECT_DIR=$PWD/
INSPECTIONS_PATH=$PWD/config/intellij-idea-inspections.xml
RESULTS_DIR=$PWD/target/inspection-results
NOISE_LVL=v1
IDEA_LOCATION=
export IDEA_PROPERTIES=$PWD/config/idea.properties

# Check IDEA_PATH env variable

if [[ ! -z $IDEA_PATH ]]; then
    IDEA_LOCATION=$IDEA_PATH
else
    echo "IDEA_PATH variable not found."
    # Try to search in path
    IDEA_LOCATION="$(which idea)"
    if [ -z $IDEA_LOCATION ]; then
        echo "IntelliJ IDEA was not found in path."
        exit -1
    fi
fi

mkdir -p $RESULTS_DIR
mkdir -p $PWD/.idea/scopes
cp $PWD/config/intellij-idea-inspection-scope.xml $PWD/.idea/scopes

exec "$IDEA_LOCATION" inspect $PROJECT_DIR $INSPECTIONS_PATH $RESULTS_DIR -$NOISE_LVL

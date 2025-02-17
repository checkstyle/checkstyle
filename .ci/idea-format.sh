#!/bin/bash

# ----------------------------------------------------------------------
# IntelliJ IDEA inspections for Checkstyle.
#
# Example Mac OS:
# IDEA_PATH="/Applications/IntelliJ IDEA.app/Contents/MacOS/idea" ./.ci/idea-inspection.sh
#
# Example Linux:
# export IDEA_PATH=$HOME/java/idea-IU-172.4574.11 && ./.ci/idea-inspection.sh
# ----------------------------------------------------------------------

PROJECT_DIR=$PWD
FORMAT_PATH="$PROJECT_DIR/config/intellij-idea-code-style.xml"
RESULTS_DIR="$PROJECT_DIR/target/format-results"

if [[ -z $IDEA_PATH ]]; then
    echo "IDEA_PATH variable not found."
    # Try to search in path
    IDEA_PATH="$(which idea)"
    if [ -z "$IDEA_PATH" ]; then
        echo "IntelliJ IDEA was not found in path."
        exit -1
    fi
fi

# Prepare results directory
if [ -d "$RESULTS_DIR" ]; then
    rm -rf "$RESULTS_DIR"
fi
mkdir -p "$RESULTS_DIR"

# Compile the project with Maven
echo "Running Maven compile..."
mvn -e --no-transfer-progress compile

# Run IDEA formatter
echo "Running IDEA format from: $IDEA_PATH"
echo "$IDEA_PATH format -r -s $FORMAT_PATH -m *.java $PROJECT_DIR"

"$IDEA_PATH/bin/idea.sh" format -r -s "$FORMAT_PATH" -m "*.java" "$PROJECT_DIR/src/it/java"
"$IDEA_PATH/bin/idea.sh" format -r -s "$FORMAT_PATH" -m "*.java" "$PROJECT_DIR/src/main/java"
"$IDEA_PATH/bin/idea.sh" format -r -s "$FORMAT_PATH" -m "*.java" "$PROJECT_DIR/src/test/java"
"$IDEA_PATH/bin/idea.sh" format -r -s "$FORMAT_PATH" -m "*.java" "$PROJECT_DIR/src/xdocs-examples/java"

if ! git diff --quiet; then
    echo "ERROR: Code formatting introduced changes in the following files:"
    git diff --name-only
    echo ""
    echo "Please run the formatter and commit the changes before proceeding."
    exit 1
else
    echo "No changes detected after formatting. All good!"
    exit 0
fi

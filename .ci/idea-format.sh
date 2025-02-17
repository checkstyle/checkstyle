#!/bin/bash

# ----------------------------------------------------------------------
# IntelliJ IDEA inspections for Checkstyle.
#
# Example usage:
# export IDEA_PATH="/Applications/IntelliJ IDEA CE.app/Contents/bin/idea.sh"
# ./idea-inspection.sh
# ----------------------------------------------------------------------

PROJECT_DIR=$(pwd)
FORMAT_PATH="$PROJECT_DIR/config/intellij-idea-code-style.xml"
RESULTS_DIR="$PROJECT_DIR/target/format-results"
IDEA_LOCATION=""

# Check IDEA_PATH env variable
if [ -n "$IDEA_PATH" ] && [ -x "$IDEA_PATH" ]; then
    IDEA_LOCATION="$IDEA_PATH"
    echo "Using IDEA from environment variable: $IDEA_LOCATION"
else
    # Try to locate idea.sh in PATH
    IDEA_LOCATION=$(which idea.sh 2>/dev/null)
    if [ -z "$IDEA_LOCATION" ]; then
        echo "IntelliJ IDEA was not found in PATH and IDEA_PATH is not set or invalid."
        exit 1
    else
        echo "Found IDEA in PATH: $IDEA_LOCATION"
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
echo "Running IDEA format from: $IDEA_LOCATION"
echo "$IDEA_LOCATION format -r -s $FORMAT_PATH -m *.java $PROJECT_DIR"

"$IDEA_LOCATION" format -r -s "$FORMAT_PATH" -m "*.java" "$PROJECT_DIR/src/it/java"
"$IDEA_LOCATION" format -r -s "$FORMAT_PATH" -m "*.java" "$PROJECT_DIR/src/main/java"
"$IDEA_LOCATION" format -r -s "$FORMAT_PATH" -m "*.java" "$PROJECT_DIR/src/test/java"
"$IDEA_LOCATION" format -r -s "$FORMAT_PATH" -m "*.java" "$PROJECT_DIR/src/xdocs-examples/java"

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

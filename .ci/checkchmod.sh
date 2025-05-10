#!/bin/bash

set -e

# Check non '.sh' and '.pl' files for unwanted executables (except mvnw and gradlew)
CHMOD=$(find -type f \
    -not -path '*/.git/*' \
    -not -path './mvnw' \
    -not -path './.ci-temp/checkstyle-samples/gradle-project/gradlew' \
    -not -name '*.sh' \
    -not -name '*.pl' \
    -a -executable 2>/dev/null)

if [[ -n $CHMOD ]]; then
    echo "Expected mode for non '.sh' files is 664.";
    echo "Files that violates this rule:"
    for NAMEFILE in $CHMOD; do
        echo "$NAMEFILE";
    done
    exit 1;
fi

# Check all '.sh' files for missing executable permission
CHMOD=$(find -type f \
    -not -path '*/.git/*' \
    -not -path './mvnw' \
    -name '*.sh' \
    -not -executable 2>/dev/null)

if [[ -n $CHMOD ]]; then
    echo "Expected mode for '.sh' files is 755.";
    echo "Files that violates this rule:"
    for NAMEFILE in $CHMOD; do
        echo "$NAMEFILE";
    done
    exit 1;
fi

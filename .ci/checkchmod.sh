#!/bin/bash

set -e

# Ensure non '.sh' files are not executable and have mode 664
CHMOD=$(find -type f -not -path '*/\.git/*' \
          -a -type f -not -name '*.sh' \
          -a -type f -not -name '*.pl' \
          -a \( -type d -not -perm 775 -o -type f -executable \))
if [[ -n $CHMOD ]]; then
    echo "Expected mode for non '.sh' files is 664.";
    echo "Files that violates this rule:"
    for NAMEFILE in $CHMOD
    do
        echo "$NAMEFILE";
    done
    exit 1;
fi

# Ensure all '.sh' files have executable bit set
CHMOD=$(find -type f -not -path '*/\.git/*' -a -type f -name '*.sh' -a -not -executable)
if [[ -n $CHMOD ]]; then
    echo "Expected mode for '.sh' files is 755.";
    echo "Files that violates this rule:"
    for NAMEFILE in $CHMOD
    do
        echo "$NAMEFILE";
    done
    exit 1;
fi

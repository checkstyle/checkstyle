#!/bin/bash

set -e

# On Travis, after clone, all files are with 644 permission, on local they are 664,
# so we check only executable bit
CHMOD=$(find -type f -not -path '*/\.git/*' \
          -a -type f -not -name '*.sh' \
          -a -type f -not -name '*.pl' \
          -a \( -type d -not -perm 775 -o -type f -executable \))
if [[ ! -z $CHMOD ]]; then
    echo "Expected mode for non '.sh' files is 664.";
    echo "Files that violates this rule:"
    for NAMEFILE in $CHMOD
    do
        echo $NAMEFILE;
    done
    exit 1;
fi

# On Travis, after clone, all 'sh' files have executable bit
CHMOD=$(find -type f -not -path '*/\.git/*' -a -type f -name '*.sh' -a -not -executable)
if [[ ! -z $CHMOD ]]; then
    echo "Expected mode for '.sh' files is 755.";
    echo "Files that violates this rule:"
    for NAMEFILE in $CHMOD
    do
        echo $NAMEFILE;
    done
    exit 1;
fi

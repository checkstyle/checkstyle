#!/bin/bash

set -e

ls -la config/
echo "-----"
find config -type f -not -perm 664
echo "-----"
find config -type f
echo "-----"
find -version
echo "-----"


CHMOD=$(find -type f -not -path '*/\.git/*' -a -type f -not -name '*.sh' -a \( -type d -not -perm 775 -o -type f -not -perm 664 \))
if [[ ! -z $CHMOD ]]; then
    echo "Expected mode for non '.sh' files is 664.";
    echo "Files that violates this rule:"
    for NAMEFILE in $CHMOD
    do
        echo $NAMEFILE;
    done
    exit 1;
fi

#!/bin/bash

set -e

CHMOD=$(find -type f -not -path '*/\.git/*' -a -type f -not -name '*.sh' -a \( -type d -not -perm 775 -o -type f -not -perm 664 \))
if [[ -z $CHMOD ]]; then
    exit 0;
else
    for NAMEFILE in $CHMOD
    do
        echo $NAMEFILE;
    done
fi
echo "  ";
echo "To change permissions.";

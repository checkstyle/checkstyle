#!/bin/bash

set -e

# ls -la config/
# echo "-----"
# find config -type f -not -perm 664
# echo "-----"
# find config -type f -perm 664
# echo "-----"
# find config -type f
# echo "-----"
# find -version
# echo "-----"
# stat -c "%a %n" config/checkstyle_checks.xml
# echo "-----"

#CHMOD=$(find -type f -not -path '*/\.git/*' -a -type f -not -path '*/\target/*' -a -type f -not -name '*.sh' -exec stat -c "%a %n" {} \; | grep -v "664" | cat)
#CHMOD=$(find -type f -not -path '*/\.git/*' -a -type f -not -name '*.sh' -a \( -type d -not -perm 775 -o \( -type f -not -perm 644 -a -type f -not -perm 664 \) \))
CHMOD=$(find -type f -not -path '*/\.git/*' -a -type f -not -name '*.sh' -a \( -type d -not -perm 775 -o -type f -executable \))
if [[ ! -z $CHMOD ]]; then
    echo "Expected mode for non '.sh' files is 664.";
    echo "Files that violates this rule:"
    for NAMEFILE in $CHMOD
    do
        echo $NAMEFILE;
    done
    exit 1;
fi

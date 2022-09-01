#!/bin/bash
: '
SuppressWithNearbyPlainTextCommentFilter
commentFormat = (default)SUPPRESS CHECKSTYLE (\\w+)
checkFormat = .*Line.*
messageFormat = (default)(null)
idFormat = (default)(null)
influenceFormat = (default)0

com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck
eachLine = true
fileExtensions = (default)

com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck
fileExtensions = (default)all files
ignorePattern = (default)^(package|import) .*
max = (default)80


 '

set -e

CHMOD=$(find -type f -not -path '*/\.git/*' \
          -a -type f -not -name '*.sh' \
          -a -type f -not -name '*.pl' \
          -a \( -type d -not -perm 775 -o -type f -executable \))
if [[ -n $CHMOD ]]; then
  # filtered violation below 'Line is longer than 80 characters (found 89).'
  echo "Expected mode for non '.sh' files is 664."; # SUPPRESS CHECKSTYLE LineLengthCheck
  echo "Files that violates this rule:"
  for NAMEFILE in $CHMOD
  do
      echo "$NAMEFILE";
  done
  exit 1;
fi

CHMOD=$(find -type f -not -path '*/\.git/*' -a \
# filtered violation below 'Line is longer than 80 characters (found 87).'
        -type f -name '*.sh' -a -not -executable) # SUPPRESS CHECKSTYLE LineLengthCheck
if [[ -n $CHMOD ]]; then
  echo "Expected mode for '.sh' files is 755.";
  # violation below 'Line contains a tab character'
	echo "Files that violates rule:" # SUPPRESS CHECKSTYLE FileTabCharacterCheck
  for NAMEFILE in $CHMOD
  do
    echo "$NAMEFILE";
  done
  # violation below 'Line contains a tab character'
	exit 1; # SUPPRESS CHECKSTYLE FileTabCharacterCheck
fi

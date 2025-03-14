/*
SuppressWithNearbyTextFilter
nearbyTextPattern = (default)SUPPRESS CHECKSTYLE (\\w+)
checkPattern = .*Line.*
messagePattern = (default)(null)
idPattern = (default)(null)
lineRange = (default)0

com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck
fileExtensions = (default)(null)
ignorePattern = (default)^(package|import) .*
max = (default)80

com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck
format = this should not appear
message = (default)(null)
ignoreCase = (default)false
minimum = (default)0
maximum = (default)0



*/
// violation 9 lines above 'Line matches the illegal pattern .*'

if [[ -n $CHMOD ]]; then
  // filtered violation below 'Line is longer than 80 characters (found 89).'
  echo "Expected mode for non '.sh' files is 664."; # SUPPRESS CHECKSTYLE LineLengthCheck
  echo "this should not appear" # SUPPRESS CHECKSTYLE RegexpSingleline
  // violation above 'Line matches the illegal pattern .*'
fi

CHMOD=$(find -type f -not -path '*/\.git/*' -a \
// filtered violation below 'Line is longer than 80 characters (found 87).'
        -type f -name '*.sh' -a -not -executable) # SUPPRESS CHECKSTYLE LineLengthCheck

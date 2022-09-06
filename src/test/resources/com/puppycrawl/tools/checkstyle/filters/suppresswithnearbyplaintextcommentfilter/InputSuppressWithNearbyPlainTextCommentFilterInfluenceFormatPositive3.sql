/*
SuppressWithNearbyPlainTextCommentFilter
commentFormat = (default)SUPPRESS CHECKSTYLE (\\w+)
checkFormat = (default).*
messageFormat = (default)(null)
idFormat = (default)(null)
influenceFormat = 3

com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck
fileExtensions = (default)all files
ignorePattern = (default)^(package|import) .*
max = (default)80


*/

-- violation below 'Line is longer than 80 characters (found 82).'
CREATE TABLE LONG_TABLE_NAME_VIOLATING_LINE_LENGTH_CHECK_FOR_TESTING_PURPOSES_ONLY

-- SUPPRESS CHECKSTYLE LineLengthCheck
CREATE TABLE LONG_TABLE_NAME -- filtered violation 'Line is longer than 80 characters (found 99).'
CREATE TABLE LONG_TABLE_NAME -- filtered violation 'Line is longer than 80 characters (found 99).'
CREATE TABLE LONG_TABLE_NAME -- filtered violation 'Line is longer than 80 characters (found 99).'
CREATE TABLE LONG_TABLE_NAME -- violation 'Line is longer than 80 characters (found 90).'

/*
SuppressWithNearbyTextFilter
nearbyTextPattern = (default)SUPPRESS CHECKSTYLE (\\w+)
checkPattern = (default).*
messagePattern = (default)(null)
idPattern = (default)(null)
influenceFormat = 3

com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck
fileExtensions = (default)all files
ignorePattern = (default)^(package|import) .*
max = (default)80

com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck
format = this should not appear
message = (default)(null)
ignoreCase = (default)false
minimum = (default)0
maximum = (default)0
ignoreComments = (default)false


*/

-- violation below 'Line is longer than 80 characters (found 82).'
CREATE TABLE LONG_TABLE_NAME_VIOLATING_LINE_LENGTH_CHECK_FOR_TESTING_PURPOSES_ONLY

-- SUPPRESS CHECKSTYLE LineLengthCheck
CREATE TABLE LONG_TABLE_NAME -- filtered violation 'Line is longer than 80 characters (found 99).'
CREATE TABLE LONG_TABLE_NAME -- filtered violation 'Line is longer than 80 characters (found 99).'
CREATE TABLE LONG_TABLE_NAME -- filtered violation 'Line is longer than 80 characters (found 99).'
CREATE TABLE LONG_TABLE_NAME -- violation 'Line is longer than 80 characters (found 90).'

CREATE TABLE "this should not appear" -- SUPPRESS CHECKSTYLE RegexpSingleline because..
CREATE TABLE "this should not appear" -- filtered violation ''Line matches the illegal pattern .*'
CREATE TABLE "this should not appear" -- filtered violation ''Line matches the illegal pattern .*'
CREATE TABLE "this should not appear" -- filtered violation ''Line matches the illegal pattern .*'

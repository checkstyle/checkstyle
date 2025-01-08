/*
SuppressWithNearbyTextFilter
nearbyTextPattern = (default)SUPPRESS CHECKSTYLE (\\w+)
checkPattern = (default).*
messagePattern = (default)(null)
idPattern = (default)(null)
lineRange = 3

com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck
fileExtensions = (default)(null)
ignorePattern = (default)^(package|import) .*
max = 92

com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck
format = this should not appear
message = (default)(null)
ignoreCase = (default)false
minimum = (default)0
maximum = (default)0



*/
// violation 9 lines above 'Line matches the illegal pattern .*'

-- SUPPRESS CHECKSTYLE LineLengthCheck
CREATE TABLE LONG_TABLE_NAME // filtered violation 'Line is longer than 92 characters (found 98).'
CREATE TABLE LONG_TABLE_NAME // filtered violation 'Line is longer than 92 characters (found 98).'
CREATE TABLE LONG_TABLE_NAME // filtered violation 'Line is longer than 92 characters (found 98).'
CREATE TABLE LONG_TABLE_NAMEEEEE // violation 'Line is longer than 92 characters (found 93).'

// filtered violation below 'Line matches the illegal pattern .*'
CREATE "this should not appear" -- SUPPRESS CHECKSTYLE RegexpSingleline because..
CREATE "this should not appear" // filtered violation 'Line matches the illegal pattern .*'
CREATE "this should not appear" // filtered violation 'Line matches the illegal pattern .*'
CREATE "this should not appear" // filtered violation 'Line matches the illegal pattern .*'

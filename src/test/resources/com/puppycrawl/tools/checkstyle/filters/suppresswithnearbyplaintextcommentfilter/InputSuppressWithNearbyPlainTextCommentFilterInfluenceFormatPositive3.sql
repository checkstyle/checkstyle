/*
SuppressWithNearbyPlainTextCommentFilter
commentFormat = (default)SUPPRESS CHECKSTYLE (\\w+)
checkFormat = (default).*
messageFormat = (default)(null)
idFormat = (default)(null)
influenceFormat = 3

com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck
eachLine = true
fileExtensions = (default)

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

SELECT
    first_name, -- SUPPRESS CHECKSTYLE FileTabCharacterCheck
	last_name, -- filtered violation 'Line contains a tab character'
	age, -- filtered violation 'Line contains a tab character'
	marital_status, -- filtered violation 'Line contains a tab character'
	income, -- violation 'Line contains a tab character'
	employee_id -- violation 'Line contains a tab character'
FROM -- SUPPRESS CHECKSTYLE FileTabCharacterCheck and LineLengthCheck
	people_london -- filtered violation 'Line contains a tab character'
WHERE
	birth_date BETWEEN '2003asdasdasd-01-01' AND '20asasdasdasdd03-12-31' -- 2 filtered violations
;

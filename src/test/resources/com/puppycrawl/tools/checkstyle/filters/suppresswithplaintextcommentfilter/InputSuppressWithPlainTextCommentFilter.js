/*
SuppressWithPlainTextCommentFilter
offCommentFormat = // CS-OFF
onCommentFormat = // CS-ON
checkFormat = (default).*
messageFormat = (default)(null)
idFormat = (default)(null)


com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck
format = .*\\s===.*
message = (default)
ignoreCase = (default)false
minimum = (default)0
maximum = (default)0
fileExtensions = (default)(null)


*/

// CS-OFF
alert( 2 === 1 ); // filtered violation

// CS-ON
alert( 2 === 1 ); // violation

alert( 2 != 1 );

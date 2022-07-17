/*
SuppressWithPlainTextCommentFilter
areaCommentFormat = SUPPRESS CHECKSTYLE
offCommentFormat = // CS-OFF
onCommentFormat = // CS-ON
checkFormat = (default).*
messageFormat = (default)(null)
idFormat = (default)(null)
influenceFormat = (default)0


com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck
format = .*\\s===.*
message = (default)
ignoreCase = (default)false
minimum = (default)0
maximum = (default)0
fileExtensions = (default)all files


*/

// CS-OFF
alert( 2 === 1 ); // filtered violation 'illegal pattern'

// CS-ON
alert( 2 === 1 ); // violation 'illegal pattern'

alert( 2 != 1 );

// filtered violation below 'illegal pattern'
alert( 2 === 1 ); // SUPPRESS CHECKSTYLE

// SUPPRESS CHECKSTYLE
alert( 2 === 1 ); // violation 'illegal pattern'

// filtered violation below 'illegal pattern'
alert( 2 === 1 ); /* SUPPRESS CHECKSTYLE */

// filtered violation below 'illegal pattern'
/* SUPPRESS CHECKSTYLE */ alert( 2 === 1 );

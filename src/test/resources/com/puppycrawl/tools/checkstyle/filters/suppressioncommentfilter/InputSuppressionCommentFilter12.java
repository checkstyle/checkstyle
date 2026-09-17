/*
SuppressionCommentFilter
offCommentFormat = SUPPRESS NAME (.*)
onCommentFormat = (default)CHECKSTYLE:ON
checkFormat = MemberName
messageFormat = $1
idFormat = (default)(null)
checkCPP = (default)true
checkC = false


com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
id = (null)
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.filters.suppressioncommentfilter;

/** Test input for a suppression comment whose matched text contains a dollar sign. */
class InputSuppressionCommentFilter12 {

    // SUPPRESS NAME low$price
    private int low$price; // violation 'Name 'low\$price' must match pattern'

}

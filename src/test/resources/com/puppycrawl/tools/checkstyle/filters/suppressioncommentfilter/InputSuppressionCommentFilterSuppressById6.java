/*
SuppressionCommentFilter
offCommentFormat = CSOFF
onCommentFormat = CSON
checkFormat = MemberNameCheck
messageFormat = (default)(null)
idFormat = (default)(null)
checkCPP = (default)true
checkC = (default)true


com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
id = ignore
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true

*/

package com.puppycrawl.tools.checkstyle.filters.suppressioncommentfilter;

public class InputSuppressionCommentFilterSuppressById6 {

    // CSOFF
    int line_length = 100; // filtered violation
    //CSON
}

/*
SuppressWithNearbyCommentFilter
commentFormat = SUPPRESS CHECKSTYLE (\\w+)(?:\\s+(\\w+))?
checkFormat = $1
messageFormat = (default)(null)
idFormat = (default)(null)
influenceFormat = 1
checkCPP = (default)true
checkC = (default)true

com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
id = (null)
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true

*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbycommentfilter;

public class InputSuppressWithNearbyCommentFilterOptionalGroup {
    // SUPPRESS CHECKSTYLE MemberNameCheck
    private int MemberName; // filtered violation
}

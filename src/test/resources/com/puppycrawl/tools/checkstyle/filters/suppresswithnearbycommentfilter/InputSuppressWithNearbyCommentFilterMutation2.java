/*
SuppressWithNearbyCommentFilter
commentFormat = SUPPRESS
checkFormat = (default).*
messageFormat = (default)(null)
idFormat = (default)(null)
influenceFormat = (default)0
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

public class InputSuppressWithNearbyCommentFilterMutation2 {
    // This should REPORT a violation because the comment 'IGNORE'
    // does not match the config 'SUPPRESS'.
    private int A; // IGNORE // violation
}

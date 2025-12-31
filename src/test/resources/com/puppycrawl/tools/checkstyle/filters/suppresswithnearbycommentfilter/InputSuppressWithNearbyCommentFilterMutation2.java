/*
SuppressWithNearbyCommentFilter
commentFormat = (default)SUPPRESS CHECKSTYLE (\\w+)
checkFormat = (default).*
messageFormat = (default)(null)
idFormat = (default)(null)
influenceFormat = (default)0
checkCPP = (default)true
checkC = (default)true


com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true

*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbycommentfilter;

/**
 * Test input for mutation testing of Tag equality.
 */
public class InputSuppressWithNearbyCommentFilterMutation2 {
    // SUPPRESS CHECKSTYLE ConstantNameCheck
    private int C1; // violation

    private int D1; // violation
}

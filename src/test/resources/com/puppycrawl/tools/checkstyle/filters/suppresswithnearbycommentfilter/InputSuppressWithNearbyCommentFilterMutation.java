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

public class InputSuppressWithNearbyCommentFilterMutation {
    // This violation (MemberName) should be suppressed by the filter
    // because the filter is configured to look for "SUPPRESS"
    private int A; // SUPPRESS
}

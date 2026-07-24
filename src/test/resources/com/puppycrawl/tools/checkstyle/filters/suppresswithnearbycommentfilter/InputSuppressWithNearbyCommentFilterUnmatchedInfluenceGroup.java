/*
SuppressWithNearbyCommentFilter
commentFormat = -@csl\\[(\\w{8,}((\\||\\(|\\))\\w+)*)\\]\\((\\d+)\\) .{10,}
checkFormat = $1
messageFormat = (default)(null)
idFormat = (default)(null)
influenceFormat = $3
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

public class InputSuppressWithNearbyCommentFilterUnmatchedInfluenceGroup {
    // -@csl[MemberNameCheck](5) my comment text
    private int InvalidName; // violation
}

/*
SuppressWithNearbyCommentFilter
commentFormat = -@csl\\[(((?:MoveVariableInside))\\((?:If\\|Else)\\))\
                (?:\\|(\\w+))?\\]\\((\\d+)\\) .{10,}
checkFormat = (default).*
messageFormat = (default)(null)
idFormat = $1
influenceFormat = $3
checkCPP = (default)true
checkC = (default)true

com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
id = MoveVariableInsideIf
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = false
applyToProtected = false
applyToPackage = false
applyToPrivate = (default)true

com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
id = MoveVariableInsideElse
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = false
applyToPackage = false
applyToPrivate = false

*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbycommentfilter;

public class InputSuppressWithNearbyCommentFilterUnmatchedInfluenceGroup {
    // -@csl[MoveVariableInside(If|Else)](5) my comment text
    private int InvalidIfName; // violation 'Name 'InvalidIfName' must match pattern'
    public int InvalidElseName; // violation 'Name 'InvalidElseName' must match pattern'
}

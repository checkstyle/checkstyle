/*
SuppressionCommentFilter
offCommentFormat = (default)CHECKSTYLE:OFF
onCommentFormat = (default)CHECKSTYLE:ON
checkFormat = (default).*
messageFormat = (default)(null)
idFormat = (default)(null)
checkCPP = (default)true
checkC = (default)true


com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.filters.suppressioncommentfilter;

class InputSuppressionCommentFilterTabs {
		int A; // CHECKSTYLE:OFF // violation
		int B; // CHECKSTYLE:ON // filtered violation
                int C; // CHECKSTYLE:OFF // violation
                int D; // CHECKSTYLE:ON // filtered violation
		/* CHECKSTYLE:OFF */ int E; // filtered violation
		/* CHECKSTYLE:ON */ int F; // violation
                /* CHECKSTYLE:OFF */ int G; // filtered violation
                /* CHECKSTYLE:ON */ int H; // violation
}

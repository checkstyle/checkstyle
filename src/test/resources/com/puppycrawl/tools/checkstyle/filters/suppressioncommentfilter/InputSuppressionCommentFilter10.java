/*
SuppressionCommentFilter
offCommentFormat = (default)CHECKSTYLE:OFF
onCommentFormat = (default)CHECKSTYLE:ON
checkFormat = e[l
messageFormat = (default)(null)
idFormat = (default)(null)
checkCPP = (default)true
checkC = (default)true


*/

package com.puppycrawl.tools.checkstyle.filters.suppressioncommentfilter;

/**
 * Test input for using comments to suppress violations.
 * @author Rick Giles
 **/
class InputSuppressionCommentFilter10
{
    final static int MYSELF = 100; // OK
    final static int myselfConstant = 1; // violation
}

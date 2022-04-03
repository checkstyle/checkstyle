/*
SuppressionCommentFilter
offCommentFormat = (default)CHECKSTYLE:OFF
onCommentFormat = (default)CHECKSTYLE:ON
checkFormat = (default).*
messageFormat = e[l
idFormat = (default)(null)
checkCPP = (default)true
checkC = (default)true


*/

package com.puppycrawl.tools.checkstyle.filters.suppressioncommentfilter;

/**
 * Test input for using comments to suppress violations.
 * @author Rick Giles
 **/
class InputSuppressionCommentFilter11
{
    final static int logger = 50; // OK
    final static int logMYSELF = 10; // violation
}

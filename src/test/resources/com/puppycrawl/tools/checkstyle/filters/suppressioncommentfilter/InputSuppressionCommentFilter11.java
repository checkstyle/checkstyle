package com.puppycrawl.tools.checkstyle.filters.suppressioncommentfilter;

/**
 * Test input for using comments to suppress violations.
 * @author Rick Giles
 **/
class InputSuppressionCommentFilter11
{
    //CHECKSTYLE:OFF
    final static int logMYSELF = 10; // violation without filter
    //CHECKSTYLE:ON
}

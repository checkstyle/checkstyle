package com.puppycrawl.tools.checkstyle.filters.suppressioncommentfilter;

/**
 * Test input for using comments to suppress violations.
 * @author Rick Giles
 **/
class InputSuppressionCommentFilter10 {
    final static int logger = 50; // OK
    //CHECKSTYLE:OFF
    final static int logMYSELF = 10; // violation without filter
    //CHECKSTYLE:ON
}

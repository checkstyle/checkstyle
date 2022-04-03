package com.puppycrawl.tools.checkstyle.filters.suppressioncommentfilter;

class InputSuppressionCommentFilter10 {
    final static int logger = 50; // OK
    //CHECKSTYLE:OFF
    final static int logMYSELF = 10; // violation wihtout filter
    //CHECKSTYLE:ON
}

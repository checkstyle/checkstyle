package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

public class InputSuppressWithPlainTextCommentFilterWithCustomOnAndOffComments {
    // cs-off
    //	has tab here

    // cs-on
    //	has tab here


    /* cs-off **/ private int	b; /* cs-on **/

    private 	int c;
}

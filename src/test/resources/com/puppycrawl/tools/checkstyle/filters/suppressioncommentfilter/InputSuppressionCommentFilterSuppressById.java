package com.puppycrawl.tools.checkstyle.filters.suppressioncommentfilter;

public class InputSuppressionCommentFilterSuppressById {

    //CSOFF ignore (reason)
    private int A1;

    // @cs-: ignore (No NPE here)
    private static final int abc = 5;


    int line_length = 100;
    //CSON ignore

    private long ID = 1;
}

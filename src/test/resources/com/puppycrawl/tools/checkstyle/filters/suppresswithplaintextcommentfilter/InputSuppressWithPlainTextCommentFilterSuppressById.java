package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

public class InputSuppressWithPlainTextCommentFilterSuppressById {

    //CSOFF ignore (reason)
    private int A1;

    // @cs-: ignore (reason)
	private static final int a1 = 5; // contains tab character

    int a2 = 100;
    //CSON ignore

    private long a3 = 1;

}

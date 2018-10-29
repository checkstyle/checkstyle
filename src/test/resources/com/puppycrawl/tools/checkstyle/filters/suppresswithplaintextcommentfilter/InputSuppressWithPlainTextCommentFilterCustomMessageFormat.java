package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

public class InputSuppressWithPlainTextCommentFilterCustomMessageFormat {

    // CHECKSTYLE:OFF
    private int A1;

	private static final int a1 = 5; // contains tab character

    int a2 = 100;

    // CHECKSTYLE:ON
    private long a3 = 1;

}

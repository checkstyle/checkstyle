/*
com.puppycrawl.tools.checkstyle.TreeWalkerTest$ViolationSuppressionCommentFilterCheck

*/
package com.puppycrawl.tools.checkstyle.treewalker;

public class InputTreeWalkerSuppressionCommentFilter {
    private int I; // violation
    /* CHECKSTYLE:OFF */
    private int J;
    /* CHECKSTYLE:ON */
    //CHECKSTYLE:OFF
    private int P; // violation
    //CHECKSTYLE:ON
}

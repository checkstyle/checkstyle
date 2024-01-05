/*
com.puppycrawl.tools.checkstyle.TreeWalkerTest$ViolationSuppressionCommentFilterCheck

*/
package com.puppycrawl.tools.checkstyle.treewalker;

public class InputTreeWalkerSuppressionCommentFilter {
    private int I; // violation, 'Name 'I' must match pattern '^[a-z][a-zA-Z0-9]*$''
    /* CHECKSTYLE:OFF */
    private int J;
    /* CHECKSTYLE:ON */
    //CHECKSTYLE:OFF
    private int P; // violation, 'Name 'P' must match pattern '^[a-z][a-zA-Z0-9]*$''
    //CHECKSTYLE:ON
}

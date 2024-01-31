/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck


com.puppycrawl.tools.checkstyle.filters.SuppressionCommentFilter
checkCPP = false

*/
package com.puppycrawl.tools.checkstyle.treewalker;

public class InputTreeWalkerSuppressionCommentFilter {
    private int I;    //violation
    /* CHECKSTYLE:OFF */
    private int J;
    /* CHECKSTYLE:ON */
    //CHECKSTYLE:OFF
    private int P;  //violation
    //CHECKSTYLE:ON
}

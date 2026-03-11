/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


com.puppycrawl.tools.checkstyle.filters.SuppressionCommentFilter
checkCPP = false

*/
package com.puppycrawl.tools.checkstyle.treewalker;

public class InputTreeWalkerSuppressionCommentFilter {
    private int I;    // violation, "Name 'I' must match pattern"
    /* CHECKSTYLE:OFF */
    private int J;
    /* CHECKSTYLE:ON */
    //CHECKSTYLE:OFF
    private int P;  // violation, "Name 'P' must match pattern"
    //CHECKSTYLE:ON
}

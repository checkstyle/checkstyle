/*
SuppressionCommentFilter
offCommentFormat = CSOFF (\\w+) \\(allow (\\w+)\\)
onCommentFormat = CSON (\\w+)
checkFormat = MemberNameCheck
messageFormat = $2
idFormat = (default)(null)
checkCPP = (default)true
checkC = (default)true


com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
id = ignore
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck
id = (null)
format = (default)^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck
illegalClassNames = (default)Error, Exception, RuntimeException, Throwable, java.lang.Error, \
                    java.lang.Exception, java.lang.RuntimeException, java.lang.Throwable


*/

package com.puppycrawl.tools.checkstyle.filters.suppressioncommentfilter;

public class InputSuppressionCommentFilterSuppressById5 {

    //CSOFF ignore (reason)
    private int A1; // violation

    // @cs-: ignore (No NPE here)
    private static final int abc = 5; // violation


    int line_length = 100; // violation
    //CSON ignore

    private long ID = 1; // violation

    // CSOFF ignore (allow DEF)
    private int DEF = 2; // filtered violation

    // CSOFF ignore (allow xyz)
    private int XYZ = 3; // violation
}

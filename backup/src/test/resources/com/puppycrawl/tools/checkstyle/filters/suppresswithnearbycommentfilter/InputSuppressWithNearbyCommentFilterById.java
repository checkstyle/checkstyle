/*
SuppressWithNearbyCommentFilter
commentFormat = @cs-: (\\w+) \\(\\w+\\)
checkFormat = (default).*
messageFormat = (default)(null)
idFormat = $1
influenceFormat = (default)0
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
package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbycommentfilter;

public class InputSuppressWithNearbyCommentFilterById {

    // filtered violation below
    private int A1; // @cs-: ignore (reason)

    // violation below
    private static final int abc = 5; // @cs-: violation (No NPE here)

    // filtered violation below
    int line_length = 100; // Suppression @cs-: ignore (reason)

    // violation below
    private long ID = 1; // Suppression @cs-:
    /*
        Suppression @cs-: ignore (reason)*/private long ID3 = 1; // filtered violation

    // violation below
    private int DEF = 4; // @cs-: ignore (allow DEF)

    // violation below
    private int XYZ = 3; // @cs-: ignore (allow xyz)
}

/*
SuppressWithNearbyCommentFilter
commentFormat = (default)SUPPRESS CHECKSTYLE (\\w+)
checkFormat = (default).*
messageFormat = (default)(null)
idFormat = (default)(null)
influenceFormat = +1
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

/**
 * Test input for using comments to suppress violations.
 *
 * @author Mick Killianey
 */
public class InputSuppressWithNearbyCommentFilterInfluenceFormat {
    // filtered violation below
    private int A1;  // SUPPRESS CHECKSTYLE MemberNameCheck

    // filtered violation below
    private int A2;  /* SUPPRESS CHECKSTYLE MemberNameCheck */
    /* SUPPRESS CHECKSTYLE MemberNameCheck */ private int A3; // filtered violation

    // filtered violation below
    private int B1;  // SUPPRESS CHECKSTYLE MemberNameCheck

    // filtered violation below
    private int B2;  /* SUPPRESS CHECKSTYLE MemberNameCheck */
    /* SUPPRESS CHECKSTYLE MemberNameCheck */ private int B3; // filtered violation

    private int C1; // violation
    // ALLOW MemberName ON NEXT LINE
    private int C2; // violation
    private int C3; // violation

    private int D1; // violation
    private int D2; // violation
    // ALLOW MemberName ON PREVIOUS LINE
    private int D3; // violation

    private static final int e1 = 0; // violation
    private int E2; // violation

    // violation below
    private int E3;    // ALLOW ConstantName UNTIL THIS LINE+2
    private static final int e4 = 0; // violation
    private int E5; // violation
    private static final int e6 = 0; // violation
    private int E7; // violation
    private int E8;    /* ALLOW MemberName UNTIL THIS LINE-3 */
    // violation above
    private static final int e9 = 0; // violation

    // ALLOW Unused UNTIL THIS LINE+5
    public static void doit1(int aInt) { // this is +1
    }

    public static void doit2(int aInt) { // this is +5
    }

    public static void doit3(int aInt) { // this is +9
    }

    public void doit4() {
        try {
            // blah blah blah
            for(int i = 0; i < 10; i++) {
                // blah blah blah
                while(true) {
                    try {
                        // blah blah blah
                    } catch(Exception e) { // violation
                        // bad bad bad
                    } catch (Throwable t) { // violation
                        // ALLOW CATCH Throwable BECAUSE I threw this together.
                    }
                }
                // blah blah blah
            }
            // blah blah blah
        } catch(Exception ex) { // violation
            // ALLOW CATCH Exception BECAUSE I am an exceptional person.
        }
    }
}

class Magic8 {
    // filtered violation below
    /* SUPPRESS CHECKSTYLE MemberNameCheck */ private int A2;/* SUPPRESS CHECKSTYLE MemberName ol */
    private int A1; // filtered violation
}

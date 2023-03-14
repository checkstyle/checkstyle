/*
SuppressionCommentFilter
offCommentFormat = (default)CHECKSTYLE:OFF
onCommentFormat = (default)CHECKSTYLE:ON
checkFormat = (default).*
messageFormat = (default)(null)
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

/**
 * Test input for using comments to suppress violations.
 * @author Rick Giles
 **/
class InputSuppressionCommentFilter2
{
    private int I; // violation

    /* CHECKSTYLE:OFF */
    private int J; // filtered violation
    /* CHECKSTYLE:ON */

    private int K; // violation

    //CSOFF: MemberNameCheck|ConstantNameCheck
    private int L; // violation
    private static final int m = 0; // violation
    /*
     * CSON: MemberNameCheck|ConstantNameCheck
     */
    private int M2;//CSOFF: ConstantNameCheck // violation
    private static final int n = 0; // violation
    //CSON: ConstantNameCheck

    //CS_OFF
    private int P; // violation
    //CS_ON

    private int Q; // violation

    //CS_OFF: ConstantNameCheck
    private int R; // violation
    private static final int s = 0; // violation
    //CS_ON

    //CHECKSTYLE:OFF
    private int T; // filtered violation
    //CHECKSTYLE:ON

    //UNUSED OFF: aInt
    public static void doit1(int aInt)
    {
    }
    //UNUSED ON: aInt
    public static void doit2(int aInt)
    {
    }

    public void doit3()
    {
        try {
            // lots of code omitted
            for(int i = 0; i < 10; i++) {
                // more code omitted
                while(true) {
                    try {
                        //CHECKSTYLE:OFF
                    } catch(Exception e) { // filtered violation
                       //CHECKSTYLE:ON
                    }
                }
                // code omitted
            }
            //CHECKSTYLE:OFF
        } catch(Exception ex) { // filtered violation
            //CHECKSTYLE:ON
        }

        try{
            //IllegalCatchCheck OFF: Exception
        } catch(RuntimeException ex){ // violation
        } catch(Exception ex){ // violation
            //IllegalCatchCheck ON: Exception
        }
    }

    public void doit4() {
        try {

        /* CHECKSTYLE:OFF */} catch(Exception e) {/* CHECKSTYLE:ON */ // filtered violation

        }
    }
}

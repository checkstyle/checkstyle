/*
LocalVariableName
format = (default)^([a-z][a-zA-Z0-9]*|_)$
allowOneCharVarInForLoop = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.localvariablename;

/**
 * Tests having inner types
 * @author Oliver Burn
 **/
class InputLocalVariableNameInnerClass
{
    // Ignore - two violations
    class InnerInner2
    {
        // Ignore
        public int fData;
    }

    // Ignore - 2 violations
    interface InnerInterface2
    {
        // Ignore - should be all upper case
        String data = "zxzc";

        // Ignore
        class InnerInterfaceInnerClass
        {
            // Ignore - need Javadoc and made private
            public int rData;

            /** needs to be made private unless allowProtected. */
            protected int protectedVariable;

            /** needs to be made private unless allowPackage. */
            int packageVariable;
        }
    }

    /** demonstrate bug in handling static final **/
    protected static Object sWeird = new Object();
    /** demonstrate bug in handling static final **/
    static Object sWeird2 = new Object();

    /** demonstrate bug in local final variable */
    public interface Inter
    {
    }

     public static void main()
     {
        Inter m = new Inter()
        {
            private static final int CDS = 1;

            private int ABC;
        };
     }

    /** annotation field incorrectly named. */
    @interface InnerAnnotation
    {
        /** Ignore - should be all upper case. */
        String data = "zxzc";
    }

    /** enum with public member variable */
    enum InnerEnum
    {
        /** First constant */
        A,

        /** Second constant */
        B;

        /** Should be private */
        public int someValue;
    }
}

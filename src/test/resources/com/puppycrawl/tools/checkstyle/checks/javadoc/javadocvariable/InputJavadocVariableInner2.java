/*
JavadocVariable
accessModifiers = public
ignoreNamePattern = (default)null
tokens = (default)ENUM_CONSTANT_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

/**
 * Tests having inner types
 * @author Oliver Burn
 */
class InputJavadocVariableInner2
{
    class InnerInner2
    {
        int fData;
    }

    // Ignore - 2 violations
    interface InnerInterface2
    {

        /** Ignore - 1 violation */
        String data = "zxzc";

        // Ignore
        class InnerInterfaceInnerClass
        {
            private int rData;

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

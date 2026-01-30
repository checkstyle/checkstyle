/*
VisibilityModifier
packageAllowed = (default)false
protectedAllowed = (default)false
publicMemberPattern = ^f[A-Z][a-zA-Z0-9]*$
allowPublicFinalFields = (default)false
allowPublicImmutableFields = (default)false
immutableClassCanonicalNames = (default)java.io.File, java.lang.Boolean, java.lang.Byte, \
                               java.lang.Character, java.lang.Double, java.lang.Float, \
                               java.lang.Integer, java.lang.Long, java.lang.Short, \
                               java.lang.StackTraceElement, java.lang.String, \
                               java.math.BigDecimal, java.math.BigInteger, \
                               java.net.Inet4Address, java.net.Inet6Address, \
                               java.net.InetSocketAddress, java.net.URI, java.net.URL, \
                               java.util.Locale, java.util.UUID
ignoreAnnotationCanonicalNames = (default)com.google.common.annotations.VisibleForTesting, \
                                 org.junit.ClassRule, org.junit.Rule


*/

package com.puppycrawl.tools.checkstyle.checks.design.visibilitymodifier;

/**
 * Tests having inner types
 * @author Oliver Burn
 **/
class InputVisibilityModifierInner
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
            // violation above 'Variable 'rData' must be private and have accessor methods.'

            /** needs to be made private unless allowProtected. */
            protected int safeField;
            // violation above 'Variable 'safeField' must be private and have accessor methods.'

            /** needs to be made private unless allowPackage. */
            int localField;
            // violation above 'Variable 'localField' must be private and have accessor methods.'
        }
    }

    /** demonstrate bug in handling static final **/
    protected static Object sWeird = new Object();
    // violation above 'Variable 'sWeird' must be private and have accessor methods.'
    /** demonstrate bug in handling static final **/
    static Object sWeird2 = new Object();
    // violation above 'Variable 'sWeird2' must be private and have accessor methods.'

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
        // violation above 'Variable 'someValue' must be private and have accessor methods.'
    }

    float fSerialVersionUID = 0x1234567F;
    // violation above 'Variable 'fSerialVersionUID' must be private and have accessor methods.'
}

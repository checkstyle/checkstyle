/*
MissingOverride
javaFiveCompatibility = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

public class InputMissingOverrideBadOverrideFromObject
{
    // violation 4 lines below 'include @java.lang.Override annotation when '@inheritDoc''
    /**
     * {@inheritDoc}
     */
    public boolean
    equals(Object obj) {
        return false;
    }


    class Junk {

        // violation 4 lines below 'include @java.lang.Override annotation when '@inheritDoc''
        /**
         * {@inheritDoc}
         */
        protected void
        finalize() throws Throwable {}
    }
}

interface HashEq2 {

    // violation 4 lines below 'include @java.lang.Override annotation when '@inheritDoc''
    /**
     * {@inheritDoc}
     */
    public int hashCode();

    // violation 4 lines below 'include @java.lang.Override annotation when '@inheritDoc''
    /**
     * {@inheritDoc}
     */
    @Deprecated
    public String toString();

    @SuppressWarnings("")
    /**
     * {@inheritDoc}
     */
    @Deprecated
    public boolean equals(Object a); // ok, because javadoc has invalid position

    @SuppressWarnings("")
    /**
     * {@inheritDoc}
     */
    public HashEq2 clone(Object a); // ok, because javadoc has invalid position
}

enum enum3 {
    B;

    // violation 4 lines below 'include @java.lang.Override annotation when '@inheritDoc''
    /**
     * {@inheritDoc}
     */
    public String toString(){
        return "B";
    }

    private static void test() {}
}

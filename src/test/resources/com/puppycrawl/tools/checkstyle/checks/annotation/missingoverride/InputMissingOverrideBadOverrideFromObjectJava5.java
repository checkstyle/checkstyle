/*
MissingOverride
javaFiveCompatibility = true


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

public class InputMissingOverrideBadOverrideFromObjectJava5
{
    /**
     * {@inheritDoc}
     */
    public boolean equals(Object obj) { // violation Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.
        return false;
    }

    /**
     * {@inheritDoc no violation}
     *
     * @inheritDocs}
     *
     * {@inheritDoc
     */
    public int hashCode() {
        return 1;
    }

    class Junk {

        /**
         * {@inheritDoc}
         */
        protected void finalize() throws Throwable {} // violation Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.
    }
}

interface HashEq2Java5 {

    /**
     * {@inheritDoc}
     */
    public int hashCode(); // violation Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.
}

enum enum3Java5 {
    B;

    /**
     * {@inheritDoc}
     */
    public String toString() { // violation Must include @java.lang.Override annotation when {@inheritDoc} Javadoc tag exists.
        return "B";
    }

    private static void test() {}
}

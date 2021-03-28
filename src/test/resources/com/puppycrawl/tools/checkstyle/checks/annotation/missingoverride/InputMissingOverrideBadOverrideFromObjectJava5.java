package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

/* Config:
 * javaFiveCompatibility = "true"
 */
public class InputMissingOverrideBadOverrideFromObjectJava5
{
    /**
     * {@inheritDoc}
     */
    public boolean equals(Object obj) {     // violation
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
        protected void finalize() throws Throwable {}       // violation
    }
}

interface HashEq2Java5 {

    /**
     * {@inheritDoc}
     */
    public int hashCode();      // violation
}

enum enum3Java5 {
    B;

    /**
     * {@inheritDoc}
     */
    public String toString() {      // violation
        return "B";
    }

    private static void test() {}
}

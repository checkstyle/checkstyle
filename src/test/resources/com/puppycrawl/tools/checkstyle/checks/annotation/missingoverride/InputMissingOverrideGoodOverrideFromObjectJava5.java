/*
MissingOverride
javaFiveCompatibility = true


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

public class InputMissingOverrideGoodOverrideFromObjectJava5
{
    /**
     * {@inheritDoc}
     */
    @Override       // ok
    public boolean equals(Object obj) {
        return false;
    }

    /**
     * {@inheritDoc no violation}
     *
     * @inheritDocs}
     *
     * {@inheritDoc
     */
    @Override       // ok
    public int hashCode() {
        return 1;
    }

    class Junk {

        /**
         * {@inheritDoc}
         */
        @Override       // ok
        protected void finalize() throws Throwable {}
    }
}

interface HashEqJava5 {

    /**
     * {@inheritDoc}
     */
    @Override       // ok
    public int hashCode();
}

enum enum1Java5 {
    B;

    /**
     * {@inheritDoc}
     */
    @Override       // ok
    public String toString() {
        return "B";
    }
}

enum enum2Java5 {
    B;

    /**
     * {@inheritDoc}
     */
    @java.lang.Override     // ok
    public String toString() {
        return "B";
    }
}

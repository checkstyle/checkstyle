/*
MissingOverride
javaFiveCompatibility = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

public class InputMissingOverrideGoodOverrideFromObject
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

interface HashEq {

    /**
     * {@inheritDoc}
     */
    @Override       // ok
    public int hashCode();
}

enum enum1 {
    B;

    /**
     * {@inheritDoc}
     */
    @Override       // ok
    public String toString() {
        return "B";
    }
}

enum enum2 {
    B;

    /**
     * {@inheritDoc}
     */
    @java.lang.Override     // ok
    public String toString() {
        return "B";
    }
}

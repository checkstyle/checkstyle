package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverride;

public class InputMissingOverrideBadOverrideFromObject
{
    /**
     * {@inheritDoc}
     */
    public boolean equals(Object obj)
    {
        return false;
    }

    /**
     * {@inheritDoc no violation}
     *
     * @inheritDocs}
     *
     * {@inheritDoc
     */
    public int hashCode()
    {
        return 1;
    }

    class Junk {

        /**
         * {@inheritDoc}
         */
        protected void finalize() throws Throwable
        {
        }
    }
}

interface HashEq2 {

    /**
     * {@inheritDoc}
     */
    public int hashCode();
}

enum Bleh3 {
    B;

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return "B";
    }

    private static void test() {}
}

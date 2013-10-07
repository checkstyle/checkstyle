package com.puppycrawl.tools.checkstyle.annotation;

public class GoodOverrideFromObject
{
    /**
     * {@inheritDoc}
     */
    @Override
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
    @Override
    public int hashCode()
    {
        return 1;
    }

    class Junk {

        /**
         * {@inheritDoc}
         */
        @Override
        protected void finalize() throws Throwable
        {
        }
    }
}

interface HashEq {

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode();
}

enum Bleh1 {
    B;

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "B";
    }
}

enum Bleh22 {
    B;

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    public String toString() {
        return "B";
    }
}

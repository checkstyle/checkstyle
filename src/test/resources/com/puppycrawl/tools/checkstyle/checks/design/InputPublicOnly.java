////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.design;

public class InputPublicOnly
{
    private interface InnerInterface
    {
        String CONST = "InnerInterface";
        
        class InnerInnerClass
        {
            private int mData;

            private InnerInnerClass()
            {
                final Runnable r = new Runnable() {
                        public void run() {};
                    };
            }
        }
    }

    private class InnerClass
    {
        private int mDiff;
    }

    private int mSize;
    int mLen;
    protected int mDeer;
    public int aFreddo;

    /** {@inheritDoc} */
    public String toString()
    {
        return super.toString();
    }

    @Deprecated @Override
    public int hashCode()
    {
        return super.hashCode();
    }
}

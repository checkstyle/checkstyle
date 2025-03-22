/*
UpperEll


*/

package com.puppycrawl.tools.checkstyle.checks.upperell;

/**
 * Test case for detecting simple semantic violations.
 * @author Lars Kühne
 **/
class InputUpperEllSemantic
{
    /* Boolean instantiation in a static initializer */
    static {
        Boolean x = new Boolean(true);
    }

    static {
        int a = 0;
    }

    static {

    }

    /** test **/
    private static final long IGNORE = 666l + 666L; // violation 'Should use uppercase 'L'.'


    public void triggerEmptyBlockWithoutBlock()
    {
        // an if statement without a block to increase test coverage
        if (true)
            return;
    }

    synchronized void foo() {
        synchronized (this) {} // not OK
        synchronized (Class.class) {
            synchronized (new Object()) {
                // not OK if checking statements
            }
        }
    }
}

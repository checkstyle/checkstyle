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
    /** test **/
    private static final long IGNORE = 666l + 666L; // violation

    public void triggerEmptyBlockWithoutBlock()
    {
        // an if statement without a block to increase test coverage
        if (true)
            return;
    }
}

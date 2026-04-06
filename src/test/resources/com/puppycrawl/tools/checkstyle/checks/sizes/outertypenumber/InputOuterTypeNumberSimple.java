/*
OuterTypeNumber
max = (default)1


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.outertypenumber; // violation
                                                 // 'Outer type number is 3 (max allowed is 1).'

/**
 * Test input with 3 outer types to trigger OuterTypeNumber violation.
 **/
final class InputOuterTypeNumberSimple
{
    /** Valid format **/
    public static final int MAX_ROWS = 2;

    /** Valid format **/
    private int mNumCreated1 = 0;

    /** constructor **/
    private InputOuterTypeNumberSimple()
    {
        // content
    }

    /** method **/
    private void method()
    {
        int variable = 0;
    }
}

/** Second outer type. */
class InputOuterTypeNumberSimple2
{
    /** Some more Javadoc. */
    public void doSomething()
    {
        for (Object obj : new java.util.ArrayList())
        {
            // empty
        }
    }
}

/** Third outer type (enum). */
enum MyEnum1
{
    /** ABC constant */
    ABC,

    /** XYZ constant */
    XYZ;

    /** Should be mSomeMember */
    private int someMember;
}

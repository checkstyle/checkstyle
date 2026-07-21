/*
OuterTypeNumber
max = 30


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.outertypenumber;

/**
 * Test input with 3 outer types, max set to 30 so no violation expected.
 **/
final class InputOuterTypeNumberSimple1
{
    /** Valid format **/
    public static final int MAX_ROWS = 2;

    /** Valid format **/
    private int mNumCreated1 = 0;

    /** constructor **/
    private InputOuterTypeNumberSimple1()
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
class InputOuterTypeNumberSimple3
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
enum MyEnum2
{
    /** ABC constant */
    ABC,

    /** XYZ constant */
    XYZ;

    /** Should be mSomeMember */
    private int someMember;
}

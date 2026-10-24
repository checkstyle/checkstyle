/*
OuterTypeNumber
max = 0


*/

package org.checkstyle.suppressionxpathfilter.sizes.outertypenumber; // violation 'Outer types defined is 3 (max allowed is 0).'

public class InputXpathOuterTypeNumber {
    int i; int j;
}
class InputOuterTypeNumberSimple2
{
    /** Some more Javadoc. */
    public void doSomething()
    {
        //"O" should be named "o"
        for (Object O : new java.util.ArrayList())
        {

        }
    }
}

/** Test enum for member naming check */
enum MyEnum1
{
    /** ABC constant */
    ABC,

    /** XYZ constant */
    XYZ;

    /** Should be mSomeMember */
    private int someMember;
}

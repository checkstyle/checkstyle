package org.checkstyle.suppressionxpathfilter.outertypenumber; //warn

public class SuppressionXpathRegressionOuterTypeNumber {
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

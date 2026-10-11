/*
PackageName
format = [A-Z]+


*/

package com.puppycrawl.tools.checkstyle.checks.naming.packagename;
// violation above 'must match pattern'

/**
 * Contains simple mistakes:
 * - Long lines
 * - Tabs
 * - Format of variables and parameters
 * - Order of modifiers
 * @author Oliver Burn
 **/
/** Test class for variable naming in for each clause. */
public class InputPackageNameSimple13
{
    /** Some more Javadoc. */
    public void doSomething()
    {
        for (Object O : new java.util.ArrayList())
        {

        }
    }
}

/** Test enum for member naming check */
enum InputPackageNameSimple4
{
    /** ABC constant */
    ABC,

    /** XYZ constant */
    XYZ;

    /** Should be mSomeMember */
    private int someMember;
}

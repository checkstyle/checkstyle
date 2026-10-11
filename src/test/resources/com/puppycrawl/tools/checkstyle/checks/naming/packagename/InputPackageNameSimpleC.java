/*
PackageName
format = (default)^[a-z]+(\\.[a-zA-Z_]\\w*)*$


*/

package com.puppycrawl.tools.checkstyle.checks.naming.packagename;

/**
 * Contains simple mistakes:
 * - Long lines
 * - Tabs
 * - Format of variables and parameters
 * - Order of modifiers
 * @author Oliver Burn
 **/
/** Test class for variable naming in for each clause. */
public class InputPackageNameSimpleC
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
enum MyEnumC
{
    /** ABC constant */
    ABC,

    /** XYZ constant */
    XYZ;

    /** Should be mSomeMember */
    private int someMember;
}

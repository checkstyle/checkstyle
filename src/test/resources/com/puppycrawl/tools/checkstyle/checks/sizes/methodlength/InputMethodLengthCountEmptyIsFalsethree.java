/*
MethodLength
max = 19
countEmpty = false
tokens = (default)METHOD_DEF , CTOR_DEF , COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.sizes.methodlength;
import java.io.*;
final class InputMethodLengthCountEmptyIsFalsethree {

}

    /** Test class for variable naming in for each clause. */
class InputSimple3
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
enum MyEnum2
{
    /** ABC constant */
    ABC,

    /** XYZ constant */
    XYZ;

    /** Should be mSomeMember */
    private int someMember;
}


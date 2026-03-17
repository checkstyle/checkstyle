/*
LocalVariableName
format = (default)^([a-z][a-zA-Z0-9]*|_)$
allowOneCharVarInForLoop = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.localvariablename;
import java.io.*;
/**
 * Contains simple mistakes:
 * - Long lines
 * - Tabs
 * - Format of variables and parameters
 * - Order of modifiers
 * @author Oliver Burn
 **/

/** Test class for variable naming in for each clause. */
class InputLocalVariableName2one
{
    /** Some more Javadoc. */
    public void doSomething()
    {
        //"O" should be named "o"
        for (Object O : new java.util.ArrayList()) // violation
        {

        }
        for (final int k_ : new int[] {}) {}
    }
}

/** Test enum for member naming check */
enum InputLocalVariableNameEnum1
{
    /** ABC constant */
    ABC,

    /** XYZ constant */
    XYZ;

    /** Should be mSomeMember */
    private int someMember;
}

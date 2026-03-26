/*
LocalFinalVariableName
format = (default)^([a-z][a-zA-Z0-9]*|_)$
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, RESOURCE


*/

package com.puppycrawl.tools.checkstyle.checks.naming.localfinalvariablename;

/**
 * Tests local final variable naming with default format.
 **/
final class InputLocalFinalVariableNameDefault
{
    /** test local variables */
    private void localVariables()
    {
        // normal decl
        int abc = 0;
        int ABC = 0;

        // final decls
        final int cde = 0;
        final int CDE = 0; // violation 'Name 'CDE' must match pattern'

        // decl in for loop init statement
        for (int k = 0; k < 1; k++)
        {
            String innerBlockVariable = "";
        }
        for (int I = 0; I < 1; I++)
        {
            String InnerBlockVariable = "";
        }
    }
}

/** Test class for variable naming in for each clause. */
class InputLocalFinalVariableNameDefault2
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
enum InputLocalFinalVariableNameDefaultEnum
{
    /** ABC constant */
    ABC,

    /** XYZ constant */
    XYZ;

    /** Should be mSomeMember */
    private int someMember;
}

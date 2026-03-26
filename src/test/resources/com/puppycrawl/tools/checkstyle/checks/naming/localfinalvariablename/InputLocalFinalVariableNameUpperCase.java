/*
LocalFinalVariableName
format = [A-Z]+
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, RESOURCE


*/

package com.puppycrawl.tools.checkstyle.checks.naming.localfinalvariablename;

/**
 * Tests local final variable naming with uppercase-only format.
 **/
final class InputLocalFinalVariableNameUpperCase
{
    /** test local variables */
    private void localVariables()
    {
        // normal decl
        int abc = 0;
        int ABC = 0;

        // final decls
        final int cde = 0; // violation 'Name 'cde' must match pattern'
        final int CDE = 0;

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
class InputLocalFinalVariableNameUpperCase3
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
enum InputLocalFinalVariableNameUpperCaseEnum
{
    /** ABC constant */
    ABC,

    /** XYZ constant */
    XYZ;

    /** Should be mSomeMember */
    private int someMember;
}

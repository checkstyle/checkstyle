/*
LocalFinalVariableName
format = (default)^([a-z][a-zA-Z0-9]*|_)$
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, RESOURCE


*/

package com.puppycrawl.tools.checkstyle.checks.naming.localfinalvariablename;

/** Test class for variable naming in for each clause. */
class InputLocalFinalVariableName2
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

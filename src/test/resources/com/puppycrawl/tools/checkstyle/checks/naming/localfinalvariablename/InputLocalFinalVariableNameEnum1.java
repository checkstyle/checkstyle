/*
LocalFinalVariableName
format = (default)^([a-z][a-zA-Z0-9]*|_)$
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, RESOURCE


*/

package com.puppycrawl.tools.checkstyle.checks.naming.localfinalvariablename;

/** Test enum for member naming check */
enum InputLocalFinalVariableNameEnum1
{
    /** ABC constant */
    ABC,

    /** XYZ constant */
    XYZ;

    /** Should be mSomeMember */
    private int someMember;
}

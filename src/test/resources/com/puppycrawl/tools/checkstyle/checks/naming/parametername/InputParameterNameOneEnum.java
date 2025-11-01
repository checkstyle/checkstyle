/*
ParameterName
format = ^a[A-Z][a-zA-Z0-9]*$
ignoreOverridden = (default)false
accessModifiers = (default)public, protected, package, private


*/

package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

/** Test enum for member naming check */
public enum InputParameterNameOneEnum
{
    /** ABC constant */
    ABC,

    /** XYZ constant */
    XYZ;

    /** Should be mSomeMember */
    private int someMember;
}

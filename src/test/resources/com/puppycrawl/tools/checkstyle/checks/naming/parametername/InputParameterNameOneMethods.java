/*
ParameterName
format = ^a[A-Z][a-zA-Z0-9]*$
ignoreOverridden = (default)false
accessModifiers = (default)public, protected, package, private


*/

package com.puppycrawl.tools.checkstyle.checks.naming.parametername;
import java.io.*;

public final class InputParameterNameOneMethods
{
    //
    // Parameter name format tests
    //

    /**
     * @return hack
     * @param badFormat1 bad format
     * @param badFormat2 bad format
     * @param badFormat3 bad format
     * @throws java.lang.Exception abc
     **/
    int test1(int badFormat1,int badFormat2, // 2 violations
              final int badFormat3)          // violation
        throws java.lang.Exception
    {
        return 0;
    }

    /** method that is 20 lines long **/
    private void longMethod()
    {
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
    }

    /** constructor that is 10 lines long **/
    private InputParameterNameOneMethods()
    {
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
        // a line
    }
}

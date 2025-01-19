/*
IllegalThrows
illegalClassNames = java.lang.Error, java.lang.Exception, NullPointerException,\
                    java.lang.IOException.
ignoredMethodNames = (default)finalize
ignoreOverriddenMethods = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalthrows;

public class InputIllegalThrowsTestIllegalClassNames {
    // violation below, 'Throwing 'NullPointerException' is not allowed'
    public void method() throws NullPointerException
    { // no code
    }

    public java.lang.Throwable methodOne() throws RuntimeException
    {
        return null;
    }
    // violation below, 'Throwing 'java.lang.Error' is not allowed'
    public void methodTwo() throws java.lang.RuntimeException, java.lang.Error
    {
    }

    public void finalize() throws Throwable {

    }
}

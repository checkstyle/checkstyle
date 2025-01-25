/*
IllegalThrows
illegalClassNames = (default)Error, RuntimeException, Throwable, java.lang.Error, \
                    java.lang.RuntimeException, java.lang.Throwable
ignoredMethodNames = (default)finalize
ignoreOverriddenMethods = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalthrows;

public class InputIllegalThrowsTestDefault {

    public void method() throws NullPointerException
    { // no code
    }
    // violation below, 'Throwing 'RuntimeException' is not allowed'
    public java.lang.Throwable methodOne() throws RuntimeException
    {
        return null;
    }
    // violation below, 'Throwing 'java.lang.RuntimeException' is not allowed'
    public void methodTwo() throws java.lang.RuntimeException,
            java.lang.Error  // violation, 'Throwing 'java.lang.Error' is not allowed'
    {
    }

    public void finalize() throws Throwable {

    }
}

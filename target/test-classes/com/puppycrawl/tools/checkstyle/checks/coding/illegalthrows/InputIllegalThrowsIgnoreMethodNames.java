/*
IllegalThrows
illegalClassNames = (default)Error, RuntimeException, Throwable, java.lang.Error, \
                    java.lang.RuntimeException, java.lang.Throwable
ignoredMethodNames = methodTwo
ignoreOverriddenMethods = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalthrows;

public class InputIllegalThrowsIgnoreMethodNames {

    public void method() throws NullPointerException
    { // no code
    }
    // violation below, 'Throwing 'RuntimeException' is not allowed'
    public java.lang.Throwable methodOne() throws RuntimeException
    {
        return null;
    }

    public void methodTwo() throws java.lang.RuntimeException, java.lang.Error
    {
    }

    public void finalize() throws Throwable { // violation, 'Throwable' is not allowed'

    }
}

/*
IllegalThrows
illegalClassNames = java.lang.Error, java.lang.Exception, NullPointerException, Throwable
ignoredMethodNames = methodTwo
ignoreOverriddenMethods = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalthrows;

public class InputIllegalThrowsTestClassNames {
    // violation below, 'Throwing 'NullPointerException' is not allowed'
    public void method() throws NullPointerException
    { // no code
    }

    public java.lang.Throwable methodOne() throws RuntimeException
    {
        return null;
    }

    public void methodTwo() throws java.lang.RuntimeException, java.lang.Error
    {
    }

    public void finalize() throws Throwable { // violation, 'Throwing 'Throwable' is not allowed'

    }
}

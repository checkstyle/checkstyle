/*
IllegalThrows
illegalClassNames = java.lang.Error, java.lang.Exception, NullPointerException,\
                    java.lang.IOException.
ignoredMethodNames = (default)finalize
ignoreOverriddenMethods = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalthrows;

public class InputIllegalThrowsTestIllegalClassNames {

    public void method() throws NullPointerException // violation
    { // no code
    }

    public java.lang.Throwable methodOne() throws RuntimeException
    {
        return null;
    }

    public void methodTwo() throws java.lang.RuntimeException, java.lang.Error // violation
    {
    }

    public void finalize() throws Throwable {

    }
}

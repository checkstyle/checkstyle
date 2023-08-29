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

    public java.lang.Throwable methodOne() throws RuntimeException // violation
    {
        return null;
    }

    public void methodTwo() throws java.lang.RuntimeException, java.lang.Error
    {
    }

    public void finalize() throws Throwable { // violation

    }
}

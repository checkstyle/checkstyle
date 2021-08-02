/*
IllegalThrows
illegalClassNames = java.lang.Error, java.lang.Exception, NullPointerException, Throwable
ignoredMethodNames = methodTwo
ignoreOverriddenMethods = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalthrows;

public class InputIllegalThrowsTestClassNames {

    public void method() throws NullPointerException // violation
    { // no code
    }

    public java.lang.Throwable methodOne() throws RuntimeException // ok
    {
        return null;
    }

    public void methodTwo() throws java.lang.RuntimeException, java.lang.Error // ok
    {
    }

    public void finalize() throws Throwable { // violation

    }
}

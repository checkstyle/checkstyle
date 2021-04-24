package com.puppycrawl.tools.checkstyle.checks.coding.illegalthrows;

/*
 * Config:
 * ignoredMethodNames = methodTwo
 */
public class InputIllegalThrowsIgnoreMethodNames {

    public void method() throws NullPointerException // ok
    { // no code
    }

    public java.lang.Throwable methodOne() throws RuntimeException // violation
    {
        return null;
    }

    public void methodTwo() throws java.lang.RuntimeException, java.lang.Error // ok
    {
    }

    public void finalize() throws Throwable { // violation

    }
}

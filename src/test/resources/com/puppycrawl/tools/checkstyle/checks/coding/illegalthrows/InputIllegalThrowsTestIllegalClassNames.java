package com.puppycrawl.tools.checkstyle.checks.coding.illegalthrows;

/*
 * Config:
 * illegalClassNames = { java.lang.Error, java.lang.Exception, NullPointerException,
 *                       java.lang.IOException }
 */
public class InputIllegalThrowsTestIllegalClassNames {

    public void method() throws NullPointerException // violation
    { // no code
    }

    public java.lang.Throwable methodOne() throws RuntimeException // ok
    {
        return null;
    }

    public void methodTwo() throws java.lang.RuntimeException, java.lang.Error // violation
    {
    }

    public void finalize() throws Throwable { // ok

    }
}

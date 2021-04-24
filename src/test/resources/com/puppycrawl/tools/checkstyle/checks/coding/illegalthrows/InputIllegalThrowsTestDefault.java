package com.puppycrawl.tools.checkstyle.checks.coding.illegalthrows;

/*
 * Config: default
 */
public class InputIllegalThrowsTestDefault {

    public void method() throws NullPointerException // ok
    { // no code
    }

    public java.lang.Throwable methodOne() throws RuntimeException // violation
    {
        return null;
    }

    public void methodTwo() throws java.lang.RuntimeException, java.lang.Error // violation
    {
    }

    public void finalize() throws Throwable { // ok

    }
}

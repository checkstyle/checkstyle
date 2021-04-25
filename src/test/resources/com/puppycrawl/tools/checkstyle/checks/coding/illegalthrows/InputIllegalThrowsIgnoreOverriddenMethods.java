package com.puppycrawl.tools.checkstyle.checks.coding.illegalthrows;

/*
 * Config:
 * ignoreOverriddenMethods = true
 */
public class InputIllegalThrowsIgnoreOverriddenMethods
             extends InputIllegalThrowsTestDefault
{
    @Override
    public void methodTwo() throws RuntimeException { // ok

    }

    @java.lang.Override
    public java.lang.Throwable methodOne() throws RuntimeException { // ok
        return null;
    }
}

package com.puppycrawl.tools.checkstyle.checks.coding.illegalthrows;

/*
 * Config:
 * ignoreOverriddenMethods = false
 */
public class InputIllegalThrowsNotIgnoreOverriddenMethod
             extends InputIllegalThrowsTestDefault
{
    @Override
    public void methodTwo() throws RuntimeException { // violation

    }

    @Override
    public java.lang.Throwable methodOne() throws RuntimeException { // violation
        return null;
    }
}

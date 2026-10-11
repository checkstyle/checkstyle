/*
IllegalThrows
illegalClassNames = (default)Error, RuntimeException, Throwable, java.lang.Error, \
                    java.lang.RuntimeException, java.lang.Throwable
ignoredMethodNames = (default)finalize
ignoreOverriddenMethods = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalthrows;

public class InputIllegalThrowsIgnoreOverriddenMethods
             extends InputIllegalThrowsTestDefault
{
    @Override
    public void methodTwo() throws RuntimeException {

    }

    @java.lang.Override
    public java.lang.Throwable methodOne() throws RuntimeException {
        return null;
    }
}

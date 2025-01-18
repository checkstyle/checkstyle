/*
IllegalThrows
illegalClassNames = (default)Error, RuntimeException, Throwable, java.lang.Error, \
                    java.lang.RuntimeException, java.lang.Throwable
ignoredMethodNames = (default)finalize
ignoreOverriddenMethods = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalthrows;

public class InputIllegalThrowsNotIgnoreOverriddenMethod
             extends InputIllegalThrowsTestDefault
{
    @Override // violation below, 'Throwing 'RuntimeException' is not allowed'
    public void methodTwo() throws RuntimeException {

    }

    @Override // violation below, 'Throwing 'RuntimeException' is not allowed'
    public java.lang.Throwable methodOne() throws RuntimeException {
        return null;
    }
}

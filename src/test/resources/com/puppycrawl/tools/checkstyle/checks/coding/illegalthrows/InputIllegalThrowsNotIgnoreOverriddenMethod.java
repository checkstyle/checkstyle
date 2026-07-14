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
    // violation 1 line below, 'Throwing 'RuntimeException' is not allowed'
    @Override
    public void methodTwo() throws RuntimeException {

    }

    // violation 1 line below, 'Throwing 'RuntimeException' is not allowed'
    @Override
    public java.lang.Throwable methodOne() throws RuntimeException {
        return null;
    }
}

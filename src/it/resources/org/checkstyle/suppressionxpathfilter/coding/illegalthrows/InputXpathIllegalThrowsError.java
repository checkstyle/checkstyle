/*
IllegalThrows
illegalClassNames = (default)Error, RuntimeException, Throwable, java.lang.Error, \
                    java.lang.RuntimeException, java.lang.Throwable
ignoredMethodNames = (default)finalize
ignoreOverriddenMethods = (default)true


*/

package org.checkstyle.suppressionxpathfilter.coding.illegalthrows;

public class InputXpathIllegalThrowsError {
    public void methodOne() throws NullPointerException
    {
    }

    public void methodTwo() throws java.lang.Error // violation 'Throwing 'java.lang.Error' is not allowed.'
    {
    }
}

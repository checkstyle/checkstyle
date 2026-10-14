/*
IllegalThrows
illegalClassNames = (default)Error, RuntimeException, Throwable, java.lang.Error, \
                    java.lang.RuntimeException, java.lang.Throwable
ignoredMethodNames = (default)finalize
ignoreOverriddenMethods = (default)true


*/

package org.checkstyle.suppressionxpathfilter.coding.illegalthrows;

public class InputXpathIllegalThrowsRuntimeException {
    public void sayHello() throws RuntimeException // violation 'Throwing 'RuntimeException' is not allowed.'
    {
    }
}

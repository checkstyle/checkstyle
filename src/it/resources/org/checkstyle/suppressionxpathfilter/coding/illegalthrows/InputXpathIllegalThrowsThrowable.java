/*
IllegalThrows
illegalClassNames = (default)Error, RuntimeException, Throwable, java.lang.Error, \
                    java.lang.RuntimeException, java.lang.Throwable
ignoredMethodNames = (default)finalize
ignoreOverriddenMethods = (default)true


*/

package org.checkstyle.suppressionxpathfilter.coding.illegalthrows;

public class InputXpathIllegalThrowsThrowable {

    Object obj=new Object() {
        public void test() throws Throwable // violation 'Throwing 'Throwable' is not allowed.'
        {
        }
    };
}

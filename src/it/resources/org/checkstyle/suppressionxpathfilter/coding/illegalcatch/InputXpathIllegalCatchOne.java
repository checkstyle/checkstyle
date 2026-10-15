/*
IllegalCatch
illegalClassNames = (default)Error, Exception, RuntimeException, Throwable, java.lang.Error, \
                    java.lang.Exception, java.lang.RuntimeException, java.lang.Throwable


*/

package org.checkstyle.suppressionxpathfilter.coding.illegalcatch;

public class InputXpathIllegalCatchOne {
    public void fun() {
        try {
        } catch (RuntimeException e) { // violation "Catching 'RuntimeException' is not allowed"
        }
    }
}

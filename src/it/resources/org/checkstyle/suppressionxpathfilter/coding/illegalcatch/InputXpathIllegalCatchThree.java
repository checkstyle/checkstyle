/*
IllegalCatch
illegalClassNames = (default)Error, Exception, RuntimeException, Throwable, java.lang.Error, \
                    java.lang.Exception, java.lang.RuntimeException, java.lang.Throwable


*/

package org.checkstyle.suppressionxpathfilter.coding.illegalcatch;

public class InputXpathIllegalCatchThree {

    static {
        try {
            int x = 0;
        } catch (Throwable e ) { // violation "Catching 'Throwable' is not allowed"
        }
    }
}

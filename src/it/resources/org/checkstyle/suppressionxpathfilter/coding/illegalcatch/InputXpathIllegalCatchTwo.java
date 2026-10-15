/*
IllegalCatch
illegalClassNames = (default)Error, Exception, RuntimeException, Throwable, java.lang.Error, \
                    java.lang.Exception, java.lang.RuntimeException, java.lang.Throwable


*/

package org.checkstyle.suppressionxpathfilter.coding.illegalcatch;

public class InputXpathIllegalCatchTwo {
    public void methodOne() {
        try {
            foo();
        } catch (java.sql.SQLException e) {
        }
    }

    private void foo() throws java.sql.SQLException {
    }

    public void methodTwo() {
        try {
        } catch (java.lang.RuntimeException e) {
        // violation above "Catching 'java.lang.RuntimeException' is not allowed"
        }
    }
}

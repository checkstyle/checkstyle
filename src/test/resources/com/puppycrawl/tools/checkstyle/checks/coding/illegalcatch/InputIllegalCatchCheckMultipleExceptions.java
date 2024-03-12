/*
IllegalCatch
illegalClassNames = java.lang.Error, java.lang.Exception, NullPointerException, \
                    OneMoreException, RuntimeException, SQLException


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegalcatch;

public class InputIllegalCatchCheckMultipleExceptions {
    public void foo() throws OneMoreException {
        try {
                foo1();
        } catch (RuntimeException | SQLException e) {} // 2 violations
        try {
                foo1();
        } catch (RuntimeException | SQLException | OneMoreException e) {} // 3 violations
        try {
                foo1();
        } catch (OneMoreException | RuntimeException | SQLException e) {} // 3 violations
        try {
                foo1();
        } catch (OneMoreException | SQLException | RuntimeException e) {} // 3 violations

    }

    private void foo1() throws RuntimeException, SQLException, OneMoreException {

    }

    private class SQLException extends Exception {

    }

    private class OneMoreException extends Exception {

    }
}

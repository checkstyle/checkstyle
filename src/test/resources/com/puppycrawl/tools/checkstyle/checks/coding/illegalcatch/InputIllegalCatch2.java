package com.puppycrawl.tools.checkstyle.checks.coding.illegalcatch;

public class InputIllegalCatch2 {
    public void foo() throws OneMoreException {
        try {
        	foo1();
        } catch (RuntimeException | SQLException e) {}
        try {
        	foo1();
        } catch (RuntimeException | SQLException | OneMoreException e) {}
        try {
        	foo1();
        } catch (OneMoreException | RuntimeException | SQLException e) {}
        try {
        	foo1();
        } catch (OneMoreException | SQLException | RuntimeException e) {}

    }
    
    private void foo1() throws RuntimeException, SQLException, OneMoreException {

    }

    private class SQLException extends Exception {

    }

    private class OneMoreException extends Exception {

    }
}

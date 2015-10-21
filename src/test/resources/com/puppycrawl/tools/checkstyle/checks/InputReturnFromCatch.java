package com.puppycrawl.tools.checkstyle.checks;

public class InputReturnFromCatch {
    public void foo() {
        try {
            System.currentTimeMillis();
        } catch (Exception e) {
            return;
        }
    }

    public void bar() {
        try {
            System.currentTimeMillis();
        } catch (Exception e) {
            if (System.currentTimeMillis() == 0) {
                return; // return from if statement
            }
        }
    }
}

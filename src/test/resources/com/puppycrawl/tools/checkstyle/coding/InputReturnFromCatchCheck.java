package com.puppycrawl.tools.checkstyle.coding;

public class InputReturnFromCatchCheck {
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

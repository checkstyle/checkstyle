/*
UnusedPrivateField

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldAnonClass {

    private int usedInAnon;
    private int unusedInAnon; // violation, 'Unused private field 'unusedInAnon''

    public void foo() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                int x = usedInAnon;
            }
        };
        r.run();
    }
}

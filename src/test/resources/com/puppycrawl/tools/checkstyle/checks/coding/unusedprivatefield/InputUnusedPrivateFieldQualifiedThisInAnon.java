/*
UnusedPrivateField

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldQualifiedThisInAnon {

    private int x;
    private int unused; // violation, 'Unused private field 'unused''

    public void foo() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                int y = InputUnusedPrivateFieldQualifiedThisInAnon.this.x;
            }
        };
        r.run();
    }
}

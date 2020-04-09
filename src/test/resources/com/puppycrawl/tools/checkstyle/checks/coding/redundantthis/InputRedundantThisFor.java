package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

/*
* Config = default
*/
public class InputRedundantThisFor {
    private String name;
    int bottom;
    int i;

    public void method1() {
        for (int i = 0; i < 10; i++) {
            this.i = i; // no violation
            this.bottom = i > 0 ? i - 1 : i; // violation
        }
    }

    public void method2() {
        for (String name : new String[]{}) {
            this.name = name; // no violation
        }

        String jarPath = new String(this.name + ".jar"); // violation
    }
}

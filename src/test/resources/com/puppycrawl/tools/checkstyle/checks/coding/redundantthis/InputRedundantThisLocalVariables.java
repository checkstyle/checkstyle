/*
RedundantThis
checkMethodCall=(default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisLocalVariables {
    private int age;
    private String a;
    private String b;
    private String[] c;

    public InputRedundantThisLocalVariables(String a, String b, String[] c) {
        if (a.length() == 1) {
            this.a = a;
            this.b = b;
            this.c = c;
        } else {
            this.a = a.substring(1);
            this.b = "123";
            this.c = null;
        }
    }

    public void update() {
        int age = 25;
        this.age = age;      // 'this' required — local variable shadows field

        if (true) {
            int age2 = 30;
            this.age = age2; // 'this' required — inner scope, field still shadowed
        }

        for (int i = 0; i < 1; i++) {
            int age3 = 35;
            this.age = age3; // 'this' required — loop scope, field still shadowed
        }
    }

    public void update2() {
        {
            int age = 6;
            {
                // empty
            }
            this.age = 1;
        }
    }
}

/*
RedundantThis
checkMethodCall=(default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisLocalVariables {
    private int age;

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
}

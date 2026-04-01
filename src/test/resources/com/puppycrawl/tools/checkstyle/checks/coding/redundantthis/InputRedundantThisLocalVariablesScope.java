/*
RedundantThis
checkMethodCall=(default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisLocalVariablesScope {
    private int age;

    public void update() {
        if (true) {
            int age = 6;
            this.age = age;
        }
        this.age = 1;
        // violation above, 'Redundant "this", variable 'age' can be accessed directly.'
    }
}

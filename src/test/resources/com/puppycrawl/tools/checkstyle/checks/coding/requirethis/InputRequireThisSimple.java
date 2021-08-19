/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisSimple {
        private final int number = 1;

    public int check() {
        int sum = number; // violation
        sum += other(); // violation
        return sum;
    }

    private int other() {
            return 0;
    }
}

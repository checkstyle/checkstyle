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
        int sum = number; // violation 'Reference to instance variable 'number' needs "this.".'
        sum += other(); // violation 'Method call to 'other' needs "this.".'
        return sum;
    }

    private int other() {
            return 0;
    }
}

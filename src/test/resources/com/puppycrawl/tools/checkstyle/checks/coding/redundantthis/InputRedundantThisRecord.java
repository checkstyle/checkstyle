/*
RedundantThis
checkMethodCall=true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public record InputRedundantThisRecord(int x, String name) {

    String describe() {
        return this.name + " " + this.x;
        //  2 violations above:
        // 'Redundant "this", field 'name' can be accessed directly.'
        // 'Redundant "this", field 'x' can be accessed directly.'
    }

    boolean isPositive() {
        return this.x > 0; // violation 'Redundant "this", field 'x' can be accessed directly.'
    }

    String format() {
        return this.describe();
        // violation above, 'Redundant "this", method 'describe' can be accessed directly.'
    }
}

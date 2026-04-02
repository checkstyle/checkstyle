/*
RedundantThis
checkMethodCall=(default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisInConstructor {

    int a;
    int b;

    public InputRedundantThisInConstructor(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public InputRedundantThisInConstructor() {
        this.a = 1; // violation, 'Redundant "this", variable 'a' can be accessed directly.'
        this.b = 2; // violation, 'Redundant "this", variable 'b' can be accessed directly.'
    }
}

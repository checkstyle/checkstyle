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

    public InputRedundantThisInConstructor(int e, int f) {
        this.a = e; // violation, 'Redundant "this", variable 'a' can be accessed directly.'
        this.b = f; // violation, 'Redundant "this", variable 'b' can be accessed directly.'
    }
}

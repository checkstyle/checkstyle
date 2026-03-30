/*
RedundantThis
checkMethodCall=(default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisBasic {
    int a = 1;
    int b = this.a;
    // violation above 'Redundant "this", field 'a' can be accessed directly.'

    void method() {
        this.a = 2;
        // violation above 'Redundant "this", field 'a' can be accessed directly.'
    }

    void method(int a) {
        this.a = a; // ok, param 'a' shadows field
        this.method(); // ok, checkMethodCall is false
    }

    public InputRedundantThisBasic() {
        this.a = 3; // violation 'Redundant "this", field 'a' can be accessed directly.'
        this.b = 4; // violation 'Redundant "this", field 'b' can be accessed directly.'
    }

    public InputRedundantThisBasic(int a, int b) {
        this.a = a; // ok, param 'a' shadows field
        this.b = b; // ok, param 'b' shadows field
    }
}

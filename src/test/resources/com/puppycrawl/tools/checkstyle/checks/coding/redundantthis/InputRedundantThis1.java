/*
RedundantThis
checkMethodCall=(default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThis1 {
    int a;

    void method() {
        this.a = 1; // violation, 'Redundant "this", variable 'a' can be accessed directly.'
    }

    void method (int a) {
        this.a = a;
        this.method();
    }
}

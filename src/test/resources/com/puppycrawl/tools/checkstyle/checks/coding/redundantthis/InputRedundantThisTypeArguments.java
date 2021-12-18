/*
RedundantThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisTypeArguments {
    int i;

    <T> void method3() {
        this.i = 5; // violation
    }

    void method4() {
        this.<String>method3(); // no violation - this is required
        this.<I>method3(); // no violation - this is required
    }
    int I = 0;
    private class I {}
}

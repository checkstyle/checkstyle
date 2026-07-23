/*
RedundantThis
checkMethodCall=(default)false

*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis.compact;

class InputRedundantThisCompact {
    int a;

    void f() {
        this.a=1;
    }
}

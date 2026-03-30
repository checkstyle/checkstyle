/*
RedundantThis
checkMethodCall=true

*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis.compact;

class InputRedundantThisCompactCheckMethodCall {
    int a;

    void f() {
        this.a=1;
    }
}

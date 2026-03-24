/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/
package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisAnonymousClassWithThis {
    int x = 0;

    void method() {
        new Object() {
            void inner() {
                int y = x;
                int z = this.x;
            }
        };
    }
}

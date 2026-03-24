/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisThreeLevelNesting {
    int field = 0;

    class Middle {
        class Inner {
            void method() {
                field = 1; // violation '.*'
            }
        }
    }
}

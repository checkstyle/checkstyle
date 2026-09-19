/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

// non-compiled with javac: cyclic inheritance

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisAnonymousCyclicInheritance {

    int field;

    void method() {
        new Cycle1() {
            void test() {
                field = 1; // violation 'Reference to instance variable 'field' needs .*'
            }
        };
    }

    interface Cycle1 extends Cycle2 {
    }

    interface Cycle2 extends Cycle1 {
    }
}

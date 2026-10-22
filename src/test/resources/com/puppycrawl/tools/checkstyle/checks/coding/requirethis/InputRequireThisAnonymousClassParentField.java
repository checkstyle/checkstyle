/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisAnonymousClassParentField {
    int f = 0;

    void method() {
        new AbstractClass() {
            public void methodInner() {
               f = 1; // ok, 'f' is inherited from AbstractClass
            }
        };
    }

    public static abstract class AbstractClass {
        int f;

        public abstract void methodInner();
    }
}

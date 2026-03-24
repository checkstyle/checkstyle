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
                f = 1; // expecting no violation - refers to AbstractClass.f, not outer class f
            }
        };
    }

    public static abstract class AbstractClass {
        int f;

        public abstract void methodInner();
    }
}

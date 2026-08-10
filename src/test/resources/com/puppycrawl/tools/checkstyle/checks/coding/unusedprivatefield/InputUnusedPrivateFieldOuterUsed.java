/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldPattern = (default)serialVersionUID

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldOuterUsed {

    private int value; // ok, private field is used

    class Inner {

        private int value; // violation 'Unused private field'

        int read() {
            return InputUnusedPrivateFieldOuterUsed.this.value;
        }
    }

    class Outer {
        private int x; // ok, private field is used
        class Inner {
            private int x; // violation 'Unused private field'
             void m() {
                 int y = Outer.this.x;
             }
        }
    }

    class Test {
        void m() {
            Object o = new java.util.HashMap() {
                private int unusedField; // violation 'Unused private field'
            };
        }
    }
}

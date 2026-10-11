/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldPattern = (default)serialVersionUID

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;
public class InputUnusedPrivateFieldQualifiedThis {
    class A {
        class B {
            private int field; // ok, private field is used

            class C {
                private int unused; // violation 'Unused private field'
                void method() {
                    int x = A.B.this.field;
                }
            }
        }
    }

}

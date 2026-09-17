/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldPattern = (default)serialVersionUID

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

// non-compiled with javac: NotAnEnclosingClass is not an actual enclosing type

public class InputUnusedPrivateFieldQualifiedThisUnresolvable {

    class Inner {
        private int field; // violation 'Unused private field'

        void method() {
            int x = NotAnEnclosingClass.this.field;
        }
    }
}

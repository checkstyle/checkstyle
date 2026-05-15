/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldPattern = (default)serialVersionUID

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldOuterUnused {

    private int value; // violation 'Unused private field'

    class Inner {

        private int value; // ok, private field is used

        int read() {
            return value;
        }
    }
    private int grandOuterField; // ok, used via qualified this from two levels down

    class Middle {
        class Innermost {
            int read() {
                return InputUnusedPrivateFieldOuterUnused.this.grandOuterField;
            }
        }
    }
}

/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisInnerClassOuterField {
    int field = 0;

    class Inner {
        void method() {
            field = 1; // violation 'Reference to instance variable 'field'
                       //  needs "InputRequireThisInnerClassOuterField.this.".'
        }
    }
}

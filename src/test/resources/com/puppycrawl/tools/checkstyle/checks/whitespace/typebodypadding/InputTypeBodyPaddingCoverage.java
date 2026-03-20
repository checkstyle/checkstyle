/*
TypeBodyPadding
starting = (default)true
ending = true
allowEmpty = false
skipInner = false
skipLocal = false
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF, ANNOTATION_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

// violation below 'A blank line is required after the opening brace of a type'
class InputTypeBodyPaddingCoverage {
    class Inner { // violation 'A blank line is required after the opening brace of a type'
        int a;
    } // violation 'A blank line is required before the closing brace of a type'

    interface EmptyInterface {} // 2 violations

    enum EmptyEnum {} // 2 violations

    public void method() {
        class Local { // violation 'A blank line is required after the opening brace of a type'
            int b;
        } // violation 'A blank line is required before the closing brace of a type'
    }
} // violation 'A blank line is required before the closing brace of a type'

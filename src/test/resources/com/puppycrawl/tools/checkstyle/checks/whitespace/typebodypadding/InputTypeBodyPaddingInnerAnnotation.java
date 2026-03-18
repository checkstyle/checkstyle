/*
TypeBodyPadding
starting = (default)true
ending = true
allowEmpty = false
skipInner = false
skipLocal = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF, ANNOTATION_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

// violation below 'A blank line is required after the opening'
@interface InputTypeBodyPaddingInnerAnnotation {
    class Nested { // violation 'A blank line is required after the opening'
        int a;
    } // violation 'A blank line is required before the closing'
} // violation 'A blank line is required before the closing'

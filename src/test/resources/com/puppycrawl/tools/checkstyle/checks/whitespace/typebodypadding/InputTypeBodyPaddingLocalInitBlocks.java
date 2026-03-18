/*
TypeBodyPadding
starting = (default)true
ending = true
allowEmpty = false
skipInner = (default)true
skipLocal = false
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF, ANNOTATION_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

// violation below 'A blank line is required after the opening'
class InputTypeBodyPaddingLocalInitBlocks {
    static {
        class StaticLocal { // violation 'A blank line is required after the opening'
            int a;
        } // violation 'A blank line is required before the closing'
    }
    {
        class InstLocal { // violation 'A blank line is required after the opening'
            int b;
        } // violation 'A blank line is required before the closing'
    }
} // violation 'A blank line is required before the closing'

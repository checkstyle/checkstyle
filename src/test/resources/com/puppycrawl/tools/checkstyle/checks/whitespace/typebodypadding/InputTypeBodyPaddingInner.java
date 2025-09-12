/*
TypeBodyPadding
starting = (default)true
ending = (default)false
allowEmpty = (default)true
skipInner = false
skipLocal = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF, ANNOTATION_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

class InputTypeBodyPaddingInner {

    int x;

    class Inner { // violation 'A blank line is required after the opening brace of a type'
        int y;
    }
}

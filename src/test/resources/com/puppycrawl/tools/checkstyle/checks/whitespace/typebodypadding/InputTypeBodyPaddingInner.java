/*
TypeBodyPadding
starting = (default)true
ending = (default)false
allowEmpty = (default)true
skipInner = false
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF, ANNOTATION_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

class InputTypeBodyPaddingInner {

    int x;

    class Inner { // violation 'A blank line is required after the opening brace of a type definition.'
        int y;
    }
}

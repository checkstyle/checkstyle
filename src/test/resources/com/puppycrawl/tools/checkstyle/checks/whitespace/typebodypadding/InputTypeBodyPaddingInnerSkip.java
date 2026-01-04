/*
TypeBodyPadding
starting = (default)true
ending = (default)false
allowEmpty = (default)true
skipInner = (default)true
skipLocal = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF, ANNOTATION_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

class InputTypeBodyPaddingInnerSkip {

    int x;

    class Inner {
        int y;
    }
}

/*
TypeBodyPadding
starting = false
ending = true
allowEmpty = (default)true
skipInner = (default)true
skipLocal = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF, ANNOTATION_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

class InputTypeBodyPaddingEnd1 {

    int x;
} // violation 'A blank line is required before the closing brace of a type definition.'

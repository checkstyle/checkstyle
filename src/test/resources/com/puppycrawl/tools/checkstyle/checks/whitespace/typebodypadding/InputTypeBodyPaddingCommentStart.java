/*
TypeBodyPadding
starting = (default)true
ending = (default)false
allowEmpty = (default)true
skipInner = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF, ANNOTATION_DEF


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

public class InputTypeBodyPaddingCommentStart {
    // Comment
    int x; // violation 'A blank line is required after the opening brace of a type definition.'

}

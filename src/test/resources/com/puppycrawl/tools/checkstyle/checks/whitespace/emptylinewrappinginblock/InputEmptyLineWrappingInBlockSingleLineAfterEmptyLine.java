/*
EmptyLineWrappingInBlock
tokens = (default) CLASS_DEF,INTERFACE_DEF,ANNOTATION_DEF,ENUM_DEF,ENUM_CONSTANT_DEF,
topSeparator = empty_line_allowed
bottomSeparator = (default)empty_line

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

public class InputEmptyLineWrappingInBlockSingleLineAfterEmptyLine {

    int field;

    class SingleLine { } // violation ''}' must have exactly one empty line before.'

}

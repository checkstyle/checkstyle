/*
EmptyLineWrappingInBlock
tokens = ENUM_CONSTANT_DEF, LITERAL_NEW
topSeparator = (default)empty_line
bottomSeparator = (default)empty_line

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

public class InputEmptyLineWrappingInBlockObjBlockNull {

    enum Color {
        RED,
        GREEN,
        BLUE { // violation 'Exactly one empty line is required after the opening brace.'
            int value;
        } // violation 'Exactly one empty line is required before the closing brace.'
    }

    void method() {
        Object o = new Object();
    }
}

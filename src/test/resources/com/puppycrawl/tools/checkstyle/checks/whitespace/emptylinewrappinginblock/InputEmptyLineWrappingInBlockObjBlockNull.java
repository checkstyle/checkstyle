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
        BLUE { // violation ''{' must have exactly one empty line after.'
            int value;
        } // violation ''}' must have exactly one empty line before'
    }

    void method() {
        Object o = new Object();
        Runnable r = new Runnable() { // violation ''{' must have exactly one empty line after.'
            @Override
            public void run() {
            }
        }; // violation ''}' must have exactly one empty line before'
    }
}

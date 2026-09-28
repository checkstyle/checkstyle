/*
EmptyLineWrappingInBlock
tokens = CLASS_DEF
topSeparator = (default)empty_line
bottomSeparator = (default)empty_line


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

public class InputEmptyLineWrappingInBlock { // violation ''{' must have exactly one empty line after.'
    private int field;

    public void method() {
        int x = 1;
    }
} // violation ''}' must have exactly one empty line before'

class InputEmptyLineWrappingInBlockSecond { // violation ''{' must have exactly one empty line after.'
    private int value;

    public int getValue() {
        return value;
    }
} // violation ''}' must have exactly one empty line before'

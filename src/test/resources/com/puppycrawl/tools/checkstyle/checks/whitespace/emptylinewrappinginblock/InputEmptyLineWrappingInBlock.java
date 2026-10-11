/*
EmptyLineWrappingInBlock
tokens = CLASS_DEF
topSeparator = (default)empty_line
bottomSeparator = (default)empty_line


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

public class InputEmptyLineWrappingInBlock {
    private int field; // violation above ''{' must have exactly one empty line after.'

    public void method() {
        int x = 1;
    }
} // violation ''}' must have exactly one empty line before'

class InputEmptyLineWrappingInBlockSecond {
    private int value; // violation above ''{' must have exactly one empty line after.'

    public int getValue() {
        return value;
    }
} // violation ''}' must have exactly one empty line before'

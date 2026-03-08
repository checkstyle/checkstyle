/*
EmptyLineWrappingInBlock
tokens = CLASS_DEF
topSeparator = no_empty_line
bottomSeparator = no_empty_line

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

public class InputEmptyLineWrappingInBlockNoEmptyLine { // violation ''{' can not have empty line after.'

    private int field;

    public void method() {
        int x = 1;
    }

} // violation ''}' can not have empty line before.'


class InputEmptyLineWrappingInBlockNoEmptyLineCorrect {
    private int field;

    public void method() {
        int x = 1;
    }
}

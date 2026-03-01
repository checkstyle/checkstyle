/*
EmptyLineWrappingInBlock
tokens = CLASS_DEF
topSeparator = no_empty_line
bottomSeparator = no_empty_line

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

public class InputEmptyLineWrappingInBlockNoEmptyLine { // violation 'No empty line is allowed after the opening brace.'

    private int field;

    public void method() {
        int x = 1;
    }

} // violation 'No empty line is allowed before the closing brace.'

/*
EmptyLineWrappingInBlock
tokens = CLASS_DEF
topSeparator = \tno_empty_line
bottomSeparator = \tno_empty_line

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

public class InputEmptyLineWrappingInBlockNoEmptyLineTrim { // violation ''{' can not have empty line after.'

    private int field;

    public void method() {
        int x = 1;
    }

} // violation ''}' can not have empty line before.'

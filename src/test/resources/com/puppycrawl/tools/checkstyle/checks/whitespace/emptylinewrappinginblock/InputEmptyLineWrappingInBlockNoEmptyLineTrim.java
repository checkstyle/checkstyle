/*
EmptyLineWrappingInBlock
tokens = CLASS_DEF, CTOR_DEF, RECORD_DEF, COMPACT_CTOR_DEF
topSeparator = \tno_empty_line
bottomSeparator = \tno_empty_line

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

// violation below ''{' can not have empty line after.'
public class InputEmptyLineWrappingInBlockNoEmptyLineTrim {

    private int field;

    public void method() {
        int x = 1;
    }

    // violation below ''{' can not have empty line after.'
    InputEmptyLineWrappingInBlockNoEmptyLineTrim() {

        int a = 0;

    } // violation ''}' can not have empty line before.'

    record Point(int x, int y) { // violation ''{' can not have empty line after.'

        Point { // violation ''{' can not have empty line after.'

            int a = 0;

        } // violation ''}' can not have empty line before.'

    } // violation ''}' can not have empty line before.'


} // violation ''}' can not have empty line before.'

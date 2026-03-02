/*
EmptyLineWrappingInBlock
tokens = CLASS_DEF
topSeparator = (default)empty_line
bottomSeparator = (default)empty_line


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class InputEmptyLineWrappingInBlock { // violation 'Exactly one empty line is required after the opening brace.'
    private int field;

    public void method() {
        int x = 1;
    }
} // violation 'Exactly one empty line is required before the closing brace.'

class InputEmptyLineWrappingInBlockSecond { // violation 'Exactly one empty line is required after the opening brace.'
    private int value;

    public int getValue() {
        return value;
    }
} // violation 'Exactly one empty line is required before the closing brace.'

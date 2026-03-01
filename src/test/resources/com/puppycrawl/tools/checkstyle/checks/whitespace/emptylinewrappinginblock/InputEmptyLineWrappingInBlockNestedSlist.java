/*
EmptyLineWrappingInBlock
tokens = SLIST
topSeparator = (default)empty_line
bottomSeparator = (default)empty_line

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

public class InputEmptyLineWrappingInBlockNestedSlist {

    public void method() {
        { // violation 'Exactly one empty line is required after the opening brace.'
            int x = 0;
        } // violation 'Exactly one empty line is required before the closing brace.'
    }
}

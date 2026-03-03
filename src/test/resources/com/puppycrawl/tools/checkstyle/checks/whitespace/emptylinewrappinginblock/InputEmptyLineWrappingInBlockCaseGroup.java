/*
EmptyLineWrappingInBlock
tokens = CASE_GROUP
topSeparator = (default)empty_line
bottomSeparator = (default)empty_line

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

public class InputEmptyLineWrappingInBlockCaseGroup {

    public void method(int value) {
        switch (value) {
            case 1:
                break;
            case 2: { // violation 'Exactly one empty line is required after the opening brace.'
                int x = 0;
            } // violation 'Exactly one empty line is required before the closing brace.'
            case 3: {

                int y = 0;

            }
            default:
                break;
        }
    }
}

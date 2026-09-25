/*
EmptyLineWrappingInBlock
tokens = LITERAL_CASE, LITERAL_DEFAULT
topSeparator = (default)empty_line
bottomSeparator = (default)empty_line

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

public class InputEmptyLineWrappingInBlockSwitchCase {

    public void method(int value) {
        switch (value) {
            case 1:
                break;
            case 2: { // violation ''{' must have exactly one empty line after.'
                int x = 0;
            } // violation ''}' must have exactly one empty line before'
            case 3: {

                int y = 0;

            }
            default: { // violation ''{' must have exactly one empty line after.'
                break;
            } // violation ''}' must have exactly one empty line before'
        }
    }
}

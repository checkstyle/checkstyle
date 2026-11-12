/*
EmptyLineWrappingInBlock
tokens = LITERAL_SWITCH
topSeparator = (default)empty_line
bottomSeparator = (default)empty_line

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

public class InputEmptyLineWrappingInBlockLiteralSwitch {

    public void noEmptyLines(int value) {
        switch (value) { // violation ''{' must have exactly one empty line after.'
            case 1:
                break;
            default:
                break;
        } // violation ''}' must have exactly one empty line before'
    }

    public void withEmptyLines(int value) {
        switch (value) {

            case 1:
                break;
            default:
                break;

        }
    }

    public int switchExpression(int value) {
        return switch (value) { // violation ''{' must have exactly one empty line after.'
            case 1 -> 1;
            default -> 0;
        }; // violation ''}' must have exactly one empty line before'
    }
}

/*
EmptyLineWrappingInBlock
tokens = LITERAL_CASE, LITERAL_DEFAULT
topSeparator = (default)empty_line
bottomSeparator = (default)empty_line

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

public class InputEmptyLineWrappingInBlockSwitchRule {

    public void method(int value) {
        switch (value) {
            case 1 -> { // violation ''{' must have exactly one empty line after.'
                int x = 0;
            } // violation ''}' must have exactly one empty line before'
            case 2 -> {

                int y = 0;

            }
            case 3 -> other(value);
            default -> { // violation ''{' must have exactly one empty line after.'
                int z = 0;
            } // violation ''}' must have exactly one empty line before'
        }
    }

    public void other(int value) {
    }

    interface Inner {

        default int get() {
            return 0;
        }

    }

    @interface Anno {

        int value() default 3;

    }
}

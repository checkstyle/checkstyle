/*
EmptyLineWrappingInBlock
tokens = LITERAL_IF, LITERAL_ELSE, LITERAL_FOR, LITERAL_WHILE, LITERAL_DO, LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_SWITCH, LITERAL_CASE, LITERAL_DEFAULT
topSeparator = (default)empty_line
bottomSeparator = (default)empty_line

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

public class InputEmptyLineWrappingInBlockLiterals {

    void allLiterals() {
        /* if: no empty lines -> violation */
        if (true) { // violation ''{' must have exactly one empty line after.'
            int a = 0;
        } // violation ''}' must have exactly one empty line before'

        /* for: with empty lines -> no violation */
        for (int i = 0; i < 1; i++) {

            int b = 0;

        }

        /* while: no empty lines -> violation */
        boolean runWhile = false;
        while (runWhile) { // violation ''{' must have exactly one empty line after.'
            int c = 0;
        } // violation ''}' must have exactly one empty line before'

        /* do: with empty lines -> no violation */
        do {

            int d = 0;

        } while (false);

        /* else: no empty lines -> violation */
        if (false) { // violation ''{' must have exactly one empty line after.'
            int x = 0;
        } else {
            // 2 violations above:
            //   ''}' must have exactly one empty line before.'
            //   ''{' must have exactly one empty line after.'
            int h = 0;
        } // violation ''}' must have exactly one empty line before'

        /* if, else if: no empty lines -> violation */
        if (false) { // violation ''{' must have exactly one empty line after.'
            int p = 0;
        } else if (true) {
            // 2 violations above:
            //   ''}' must have exactly one empty line before.'
            //   ''{' must have exactly one empty line after.'
            int q = 0;
        } // violation ''}' must have exactly one empty line before'

        /* try: compliant, catch: violation, finally: compliant */
        try {

            int e = 0;

        } catch (Exception ex) { // violation ''{' must have exactly one empty line after.'
            int f = 0;
        } finally { // violation ''}' must have exactly one empty line before'

            int g = 0;

        }

        /* switch: with empty lines -> no violation */
        switch (0) {

            case 0:
                break;
            default:
                break;

        }
    }
}

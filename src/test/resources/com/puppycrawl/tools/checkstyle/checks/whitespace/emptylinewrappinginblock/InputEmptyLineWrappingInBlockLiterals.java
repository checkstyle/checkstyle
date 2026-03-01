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
        if (true) { // violation 'Exactly one empty line is required after the opening brace.'
            int a = 0;
        } // violation 'Exactly one empty line is required before the closing brace.'

        /* for: with empty lines -> no violation */
        for (int i = 0; i < 1; i++) {

            int b = 0;

        }

        /* while: no empty lines -> violation */
        while (false) { // violation 'Exactly one empty line is required after the opening brace.'
            int c = 0;
        } // violation 'Exactly one empty line is required before the closing brace.'

        /* do: with empty lines -> no violation */
        do {

            int d = 0;

        } while (false);

        /* else: no empty lines -> violation */
        if (false) { // violation 'Exactly one empty line is required after the opening brace.'
            int x = 0;
        } else {
            // 2 violations above:
            //   'Exactly one empty line is required before the closing brace.'
            //   'Exactly one empty line is required after the opening brace.'
            int h = 0;
        } // violation 'Exactly one empty line is required before the closing brace.'

        /* if, else if: no empty lines -> violation */
        if (false) { // violation 'Exactly one empty line is required after the opening brace.'
            int p = 0;
        } else if (true) {
            // 2 violations above:
            //   'Exactly one empty line is required before the closing brace.'
            //   'Exactly one empty line is required after the opening brace.'
            int q = 0;
        } // violation 'Exactly one empty line is required before the closing brace.'

        /* try: compliant, catch: violation, finally: compliant */
        try {

            int e = 0;

        } catch (Exception ex) { // violation 'Exactly one empty line is required after the opening brace.'
            int f = 0;
        } finally { // violation 'Exactly one empty line is required before the closing brace.'

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

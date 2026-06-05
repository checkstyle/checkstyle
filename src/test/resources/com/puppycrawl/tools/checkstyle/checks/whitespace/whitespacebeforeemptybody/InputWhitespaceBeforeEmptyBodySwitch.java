/*
WhitespaceBeforeEmptyBody
tokens = LITERAL_SWITCH


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

public class InputWhitespaceBeforeEmptyBodySwitch {

    void test(int x) {

        // violation below ''{' is not preceded with whitespace'
        switch (x){ /* empty */ }

        switch (x){} // violation ''{' is not preceded with whitespace'

        switch (x) {
            case 1:
                x = 2;
                break;
            default:
                x = 0;
                break;
        }
    }
}

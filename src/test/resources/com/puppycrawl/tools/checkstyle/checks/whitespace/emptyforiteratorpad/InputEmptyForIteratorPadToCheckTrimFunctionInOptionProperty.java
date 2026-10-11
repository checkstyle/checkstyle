/*
EmptyForIteratorPad
option = \tSPACE


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptyforiteratorpad;

class InputEmptyForIteratorPadToCheckTrimFunctionInOptionProperty {

    void method() {

        for (int i = 0; i < 1;i++ ) {
        }

        for (int i = 0; i < 1; i++ ) {
        }

        for (int i = 0; i < 1;) { // violation '';' is not followed by whitespace'
            i++;
        }

        for (int i = 0; i < 1; ) {
            i++;
        }
    }
}

/*
EmptyForIteratorPad
option = space


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptyforiteratorpad;

public class InputEmptyForIteratorPadToCheckUppercaseFunctionInOptionProperty {

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

/*
EmptyForIteratorPad
option = (default)nospace


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptyforiteratorpad;

class InputEmptyForIteratorPad
{
    void method1()
    {
        for (int i = 0; i < 1; i++) {
        }

        for (int i = 0; i < 1;i++) {
        }

        for (int i = 0; i < 1;i++ ) {
        }

        for (int i = 0; i < 1; i++ ) {
        }

        for (int i = 0; i < 1;) {
            i++;
        }

        for (int i = 0; i < 1; ) { // violation
            i++;
        }

        // test eol, there is no space after second SEMI
        for (int i = 0; i < 1;
            ) {
            i++;
        }
    }

    void method2()
    {
        for ( int i = 0; i < 1; i++ ) {
        }

        for ( int i = 0; i < 1; ) { // violation
            i++;
        }

        int i = 0;
        for ( ; i < 1; i++ ) {
        }

        for (; i < 2; i++ ) {
        }

        for (
        ;; ) { // violation
        }
    }
}

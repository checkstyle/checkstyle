/*
EmptyForInitializerPad
option = invalid_option


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptyforinitializerpad;

class InputEmptyForInitializerPad2 // violation
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

        for (int i = 0; i < 1; ) {
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

        for ( int i = 0; i < 1; ) {
            i++;
        }

        int i = 0;
        for ( ; i < 1; i++ ) {
        }

        for (; i < 2; i++ ) {
        }

        for (
        ;; ) {
        }
    }
}

/*
EmptyForInitializerPad
option = \tspace


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptyforinitializerpad;

public class InputEmptyForInitializerPadSetOptionTrim {
    void method2() {
        for ( int i = 0; i < 1; i++ ) {
        }
        int i = 0;
        for (; i < 2; i++ ) { // violation '';' is not preceded with whitespace'
        }
    }
}

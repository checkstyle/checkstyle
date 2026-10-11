/*
RightCurly
option = ALONE
tokens = LITERAL_SWITCH, METHOD_DEF, LITERAL_WHILE, LITERAL_IF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyTestWithComment {
    void m1(int mode) {
        switch (mode) {
            default:
                int x = 0; // violation below ''}' at column 66 should be alone on a line'
                /* block comment here is counted as violation */ }
    }

    void m2() {
        int a = 0;
        if (a > 2) {
            System.out.println();  // violation below ''}' at column 61 should be alone on a line'
            /* block comment here is counted as violation*/ }
        /* block comment here is counted as violation*/ }
        // violation above ''}' at column 57 should be alone on a line'

    void method() {
        int a = 0;
        if (a > 2) {
            System.out.println("no");
        /* comment */ } // violation ''}' at column 23 should be alone on a line'
    /* commrnt */ } // violation ''}' at column 19 should be alone on a line'

    void loop() {
        while(true) {
            System.out.println("Checkstyle");
        /* comment */ } // violation ''}' at column 23 should be alone on a line'
    /* comment */ /* comment */ } // violation ''}' at column 33 should be alone on a line'
}

/*
OneStatementPerLine
treatTryResourcesAsStatement = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

public class InputOneStatementPerLineBreakAndContinueStatements {

    void method() {
        int i = 10;
        while (i > 0) {
            i--;
            if (i == 2) {
                // violation below 'Only one statement per line allowed.'
                int a = 1; break
                ;
            }
        }

        for (i = 0; i < 10; i++) {
            if (i % 2 == 1) {
                // violation 2 lines below 'Only one statement per line allowed.'
                int
                a = 0; continue
                ;
            }
        }
    }

    void foo() {
        int a = 2;
        do {
            a *= 2;
            if (a > 50) {
               // violation below 'Only one statement per line allowed.'
               int k = a; break
               ;
            }
        } while (a < 100);
    }
}

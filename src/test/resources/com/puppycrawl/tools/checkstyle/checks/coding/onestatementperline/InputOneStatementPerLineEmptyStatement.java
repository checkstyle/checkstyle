/*
OneStatementPerLine
treatTryResourcesAsStatement = (default)false


*/


package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

public class InputOneStatementPerLineEmptyStatement {
    void testEmptyStatement() {
        ; ;                               // violation 'Only one statement per line allowed.'
    }

    void testNonEmptyStatement() {
        int x = 0; int y = 1; int z = 2;  // violation 'Only one statement per line allowed.'
    }                                     // violation above 'Only one statement per line allowed.'

    void testMixed() {
                                          // violation below 'Only one statement per line allowed.'
        ; ; int x = 0;                    // violation 'Only one statement per line allowed.'
                                          // violation below 'Only one statement per line allowed.'
        ; int y = 0; ;                    // violation 'Only one statement per line allowed.'
        int z = 0;;;                      // violation 'Only one statement per line allowed.'
                                          // violation above 'Only one statement per line allowed.'
    }
}

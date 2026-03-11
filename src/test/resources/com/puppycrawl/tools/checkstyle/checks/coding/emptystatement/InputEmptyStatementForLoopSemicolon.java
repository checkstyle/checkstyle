/*
EmptyStatement


*/

package com.puppycrawl.tools.checkstyle.checks.coding.emptystatement;

public class InputEmptyStatementForLoopSemicolon {
    void test() {
        for (;;) { // semicolons here are part of for syntax, not empty statements
        }
    }
}

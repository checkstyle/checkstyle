/*
EmptyStatement


*/

package com.puppycrawl.tools.checkstyle.checks.coding.emptystatement;

public class InputEmptyStatementNonTypeSemicolon {
    public void method() {
        for (int i = 0; i < 1; i++) {
            int x = 0;
            x++;
        }
        return;
    }
}

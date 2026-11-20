/*
EmptyStatement
allowEmptyLoopBody = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.emptystatement;

public class InputEmptyStatementAllowEmptyLoopBody {

    public void test(boolean condition) {
        for (; condition;);
        while (condition);
        do; // violation 'Empty statement'
        while (condition);
        foo();; // violation 'Empty statement'
    }

    private void foo() {
    }

}

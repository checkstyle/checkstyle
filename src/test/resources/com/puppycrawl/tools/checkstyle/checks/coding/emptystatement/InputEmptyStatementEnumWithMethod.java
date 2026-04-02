/*
EmptyStatement


*/

package com.puppycrawl.tools.checkstyle.checks.coding.emptystatement;

public enum InputEmptyStatementEnumWithMethod {
    MONDAY,
    TUESDAY;

    public void method() {
    };  // violation 'Empty statement'
}

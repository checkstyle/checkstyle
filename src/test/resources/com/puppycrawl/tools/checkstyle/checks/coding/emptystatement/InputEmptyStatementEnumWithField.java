/*
EmptyStatement


*/

package com.puppycrawl.tools.checkstyle.checks.coding.emptystatement;

public enum InputEmptyStatementEnumWithField {
    MONDAY,
    TUESDAY;
    int x;
    ; // violation 'Empty statement'
}

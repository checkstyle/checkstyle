/*
EmptyStatement


*/

package com.puppycrawl.tools.checkstyle.checks.coding.emptystatement;

public enum InputEmptyStatementEnumTrailingCommaSemicolon {
    GOOD("localhost"),
    BAD("127.0.0.1"),
    ;

    private final String value;

    InputEmptyStatementEnumTrailingCommaSemicolon(String value) {
        this.value = value;
    }
}

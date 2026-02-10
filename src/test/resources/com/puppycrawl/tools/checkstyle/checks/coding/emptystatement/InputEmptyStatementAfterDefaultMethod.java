/*
EmptyStatement


*/

package com.puppycrawl.tools.checkstyle.checks.coding.emptystatement;

public interface InputEmptyStatementAfterDefaultMethod {
    @Deprecated
    default String test(String value) {
        return value;
    };
}

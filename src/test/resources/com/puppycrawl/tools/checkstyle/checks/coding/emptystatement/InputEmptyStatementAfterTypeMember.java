/*
EmptyStatement


*/

package com.puppycrawl.tools.checkstyle.checks.coding.emptystatement;

public class InputEmptyStatementAfterTypeMember {
    int field = 1;;

    static {
        int number = 1;
    };

    void method() {};
}

interface InputEmptyStatementAfterTypeMemberInterface {
    default String quote(String value) {
        return value;
    };
}

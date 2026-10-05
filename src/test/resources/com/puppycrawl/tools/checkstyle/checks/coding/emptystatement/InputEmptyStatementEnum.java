/*
EmptyStatement


*/

package com.puppycrawl.tools.checkstyle.checks.coding.emptystatement;

public class InputEmptyStatementEnum {

    enum Constants {
        FIRST,
        SECOND;
    }

    enum TrailingComma {
        FIRST,
        SECOND,
        ;; // violation 'Empty statement'
    }

    enum NoConstantsWithMembers {
        ;

        private final int value = 0;

        int getValue() {
            return value;
        }; // violation 'Empty statement'
    }

    enum ExtraSemicolon {
        FIRST;
        ; // violation 'Empty statement'
    }

    enum ConstantBody {
        FIRST {
            ; // violation 'Empty statement'
        }
    }
}

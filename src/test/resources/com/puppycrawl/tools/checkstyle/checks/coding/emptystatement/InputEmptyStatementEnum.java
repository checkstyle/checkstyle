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
        ;
    }

    enum NoConstantsWithMembers {
        ;

        private final int value = 0;
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

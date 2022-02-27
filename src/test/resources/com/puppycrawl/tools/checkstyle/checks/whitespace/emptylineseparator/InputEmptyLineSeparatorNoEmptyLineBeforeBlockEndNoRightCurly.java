/*
EmptyLineSeparator
requireEmptyLineAfterBlockStart = true
allowNoEmptyLineBeforeBlockEnd = true
allowMultipleEmptyLines = true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/

class InputEmptyLineSeparatorNoEmptyLineBeforeBlockEndNoRightCurly {
    InputEmptyLineSeparatorNoEmptyLineBeforeBlockEndNoRightCurly() { // violation
    }

    void method() {
        int i = 0;
        while (i++ == 0) {

        } // no violation because RCURLY not in tokens

    } // no violation because RCURLY not in tokens

} // no violation because RCURLY not in tokens

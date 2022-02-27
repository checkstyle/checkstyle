/*
EmptyLineSeparator
requireEmptyLineAfterBlockStart = (default)false
allowNoEmptyLineBeforeBlockEnd = true
allowMultipleEmptyLines = true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF, RCURLY


*/

class InputEmptyLineSeparatorNoEmptyLineBeforeBlockEnd {
    static { // no violation, check disabled

    } // violation

    InputEmptyLineSeparatorNoEmptyLineBeforeBlockEnd() {
    }

    InputEmptyLineSeparatorNoEmptyLineBeforeBlockEnd(boolean b) {

        // Comment inhibits violation.
    }

    void method() {
        int i = 0;
        while (i++ == 0) {

        } // violation

    } // violation

} // violation

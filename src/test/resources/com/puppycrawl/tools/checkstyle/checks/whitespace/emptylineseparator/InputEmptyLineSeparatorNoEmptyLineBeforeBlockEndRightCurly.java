/*
EmptyLineSeparator
requireEmptyLineAfterBlockStart = (default)false
allowNoEmptyLineBeforeBlockEnd = (default)false
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF, RCURLY


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

class InputEmptyLineSeparatorNoEmptyLineBeforeBlockEndRightCurly {
    InputEmptyLineSeparatorNoEmptyLineBeforeBlockEndRightCurly() { // no violation
        int i = 0; // no violation
    } // violation


}

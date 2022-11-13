/*
EmptyLineSeparator
requireEmptyLineAfterBlockStart = true
allowNoEmptyLineBeforeBlockEnd = (default)false
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

class InputEmptyLineSeparatorEmptyLineAfterBlockStart {
    static { // violation
    }

    static class Class1 {


        int i = 0; // violation
    }

    static class Class2 {
        static int i = 0; // violation
    }
    static class Class3 { // violation
        Class3() { // violation
        }
    }

    static class Class4 {

        Class4() {
        }
    }

    static class Class5 {

        int i = 0;
    }
}

/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF, ENUM_CONSTANT_DEF,


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

public class InputEmptyLineSeparatorEnumSharedLine {

    private enum State1 {
        FIRST,


        SECOND, THIRD; // violation ''ENUM_CONSTANT_DEF' has more than 1 empty lines before.'
    }

    private enum State2 {
        ONE, TWO,


        THREE, FOUR, FIVE; // violation ''ENUM_CONSTANT_DEF' has more than 1 empty lines before.'
    }
}

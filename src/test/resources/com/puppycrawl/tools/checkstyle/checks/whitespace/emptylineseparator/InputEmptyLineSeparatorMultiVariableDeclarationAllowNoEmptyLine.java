/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = true
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

public class InputEmptyLineSeparatorMultiVariableDeclarationAllowNoEmptyLine {
    int a = 1, b = 2;

    int c = 3,
        d = 4;

    int e = 5;
    int f = 6;
}

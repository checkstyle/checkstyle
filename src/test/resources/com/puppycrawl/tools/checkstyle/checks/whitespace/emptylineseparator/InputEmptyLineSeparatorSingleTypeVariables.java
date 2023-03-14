/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = true
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;


public class InputEmptyLineSeparatorSingleTypeVariables {
    int a; // ok
    int b; // ok
    int c; // ok
    int a1, b1, c1; // ok

}

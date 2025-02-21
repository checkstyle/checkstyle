/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = false
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

public interface InputEmptyLineSeparatorInterfaceFields {
    int a = 45;

    int b = 45;


    int c = 45; // violation ''VARIABLE_DEF' has more than 1 empty lines before.'



    int d = 45; // violation ''VARIABLE_DEF' has more than 1 empty lines before.'
}

@interface Ann {
    int a = 45;

    int b = 45;


    int c = 45; // violation ''VARIABLE_DEF' has more than 1 empty lines before.'



    int d = 45; // violation ''VARIABLE_DEF' has more than 1 empty lines before.'
}

interface Abc {
    Object apply(Object array, int index);

    Abc A = (o, i) -> new Object();
    Abc B = (o, i) -> o; // violation ''VARIABLE_DEF' should be separated from previous line.'
}

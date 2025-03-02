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

public class InputEmptyLineSeparatorEnumMembers {}

enum A {
    ONE("one"),
    TWO("two");


    private final String str; // violation ''VARIABLE_DEF' has more than 1 empty lines before.'

    private String otherString;


    private String thirdString; // violation ''VARIABLE_DEF' has more than 1 empty lines before.'
    private String fourth; // violation ''VARIABLE_DEF' should be separated from previous line.'


    A(String s) { // violation ''CTOR_DEF' has more than 1 empty lines before.'
        this.str = s;
    }

    private String fifth;
    static { // violation ''STATIC_INIT' should be separated from previous line.'
    }


    static { // violation ''STATIC_INIT' has more than 1 empty lines before.'

    }

    public String getStr() {
        return str;
    }
}

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

enum InputEmptyLineSeparatorEnumMembers {

    ONE("one"),
    TWO("two");


    private final String someString; // violation

    private String otherString; // ok


    private String thirdString; // violation


    InputEmptyLineSeparatorEnumMembers(String someString) { // violation
        this.someString = someString;
    }

    private String fourth;


    static { // violation

    }

    public String getSomeString() {
        return someString;
    }
}

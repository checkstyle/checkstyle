/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, ENUM_CONSTANT_DEF, STATIC_INIT, INSTANCE_INIT, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

public class InputEmptyLineSeparatorWithCommentsEnums {


    // violation ''//' has more than 1 empty lines before.'
    public enum Enum1 {
        E1, E2
    }

    // ok, because no more than 1 empty lines before
    public enum Enum2 { }


    // violation ''//' has more than 1 empty lines before.'
    // ok, because no more than 1 empty lines before

    // ok, because no more than 1 empty lines before
    public enum Enum3 { }
    // ok, because no more than 1 empty lines before

    public enum Enum4 { }
    // ok, because no more than 1 empty lines before

    public enum Enum5 { }


    // violation ''//' has more than 1 empty lines before.'

    public


    // violation ''//' has more than 1 empty lines before.'
    static

    enum Enum6 { }
}

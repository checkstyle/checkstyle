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

public class InputEmptyLineSeparatorWithCommentsInterfaces {

    // ok, because no more than 1 empty lines before
    public interface Interface1 { }


    // violation ''//' has more than 1 empty lines before.'
    public interface Interface2 { }
    // violation below ''INTERFACE_DEF' should be separated from previous line.'
    public
    // .
    interface Interface3 { }


    // violation ''//' has more than 1 empty lines before.'
    /*
     */
    /* . */
    /* . */
    /* . */
    interface Interface4 { }

    // ok, because no more than 1 empty lines before

    // ok, because no more than 1 empty lines before
    // .
    // .
    // .
    interface Interface5 { }
}

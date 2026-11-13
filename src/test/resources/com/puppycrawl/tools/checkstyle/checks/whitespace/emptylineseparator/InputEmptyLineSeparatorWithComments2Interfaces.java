/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, ENUM_CONSTANT_DEF, STATIC_INIT, INSTANCE_INIT, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

public class InputEmptyLineSeparatorWithComments2Interfaces {


    public interface Interface1 { }



    public interface Interface2 { }
    // some comment
    public // violation ''INTERFACE_DEF' should be separated from previous line.'
    // .
    interface Interface3 { }


    /* something */
    /* . */
    /* . */
    /* . */
    interface Interface4 { }




    // .
    // .
    // .
    interface Interface5 { }
}

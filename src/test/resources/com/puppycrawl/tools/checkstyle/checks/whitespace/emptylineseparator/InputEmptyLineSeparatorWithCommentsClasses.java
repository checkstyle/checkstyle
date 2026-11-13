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

public class InputEmptyLineSeparatorWithCommentsClasses {

    public static class Class1 { }


    public static class Class2 { } // violation ''CLASS_DEF' has more than 1 empty lines before.'

    // ok, because no more than 1 empty lines before
    public static class Class3 { }


    // violation ''//' has more than 1 empty lines before.'
    public static class Class4 { }


    // violation ''//' has more than 1 empty lines before.'
    public
    // ok, because no more than 1 empty lines before
    static class Class5 { }


    // violation ''//' has more than 1 empty lines before.'
    public
    /* something */
    static class Class6 { }

    /*
     * Should not have
     * violation
     */
    public static class Class7 { }


    // violation ''//' has more than 1 empty lines before.'
    /*
     * Should have
     * violation
     */
    public static class Class8 { }

    /**
     * Should not have
     * violation
     */
    public static class Class9 { }


    // violation ''//' has more than 1 empty lines before.'
    /**
     * Should have
     * violation
     */
    public static class Class10 {
        {

        }
    }
}

/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = false
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

public abstract class InputEmptyLineSeparatorMultipleEmptyLinesInside
{
    public InputEmptyLineSeparatorMultipleEmptyLinesInside() {
        // violation


    }

    private int counter;

    private Object obj = null;

    abstract int generateSrc(String s);

    static {
        // violation


    }

    {
        // violation


    }

    private static void foo() { // violation


        // 1 empty line above should cause a violation

        // violation



        // 2 empty lines above should cause violations
    }
}
class SecondClassReturnWithVeryVeryVeryLongName{} // violation

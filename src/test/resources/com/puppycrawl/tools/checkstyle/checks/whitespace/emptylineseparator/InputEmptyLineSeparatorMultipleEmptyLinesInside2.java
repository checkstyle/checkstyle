/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

public abstract class InputEmptyLineSeparatorMultipleEmptyLinesInside2
{
    public InputEmptyLineSeparatorMultipleEmptyLinesInside2() {
        // empty lines below should cause a violation


    }

    private int counter;

    private Object obj = null;

    abstract int generateSrc(String s);

    static {
        // empty lines below should cause a violation


    }

    {
        // empty lines below should cause a violation


    }

    private static void foo() {


        // 1 empty line above should cause a violation

        // 1 empty line above should not cause a violation



        // 2 empty lines above should cause violations
    }
}
class SecondClassReturnWithVeryVeryVeryLongName2{} // violation

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
        // empty lines below


    }

    private int counter;

    private Object obj = null;

    abstract int generateSrc(String s);

    static {
        // empty lines below


    }

    {
        // empty lines below


    }

    private static void foo() {


        // 1 empty line above

        // 1 empty line above



        // 2 empty lines above
    }
}
class // violation ''CLASS_DEF' should be separated from previous line.'
SecondClassReturnWithVeryVeryVeryLongName2{}

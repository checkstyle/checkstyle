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
        // violation 'There is more than 1 empty line after this line.'


    }

    private int counter;

    private Object obj = null;

    abstract int generateSrc(String s);

    static {
        // violation 'There is more than 1 empty line after this line.'


    }

    {
        // violation 'There is more than 1 empty line after this line.'


    }

    private static void foo() { // violation 'There is more than 1 empty line after this line.'


        // 1 empty line above should cause a violation

        // violation 'There is more than 1 empty line after this line.'



        // 2 empty lines above should cause violations
    }
}
class // violation ''CLASS_DEF' should be separated from previous line.'
SecondClassReturnWithVeryVeryVeryLongName{}

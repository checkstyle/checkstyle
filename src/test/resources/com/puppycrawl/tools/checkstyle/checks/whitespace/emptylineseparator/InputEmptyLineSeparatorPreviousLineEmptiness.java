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

public class InputEmptyLineSeparatorPreviousLineEmptiness {
    private static final int MULTIPLICATOR;

    private int base;

    static {
        MULTIPLICATOR = 5; // violation


    }

    { // violation


        base = 33;
    }

    public InputEmptyLineSeparatorPreviousLineEmptiness(int base) { // violation


        this.base = base;
    }

    public InputEmptyLineSeparatorPreviousLineEmptiness() {
    }

    public static int increment(int value) { // violation


        return value * MULTIPLICATOR + 1;
    }

    void foo2() { // ok
    }


    void foo3() { // violation


    }
}

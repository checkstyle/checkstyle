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
        MULTIPLICATOR = 5; // violation 'There is more than 1 empty line after this line'


    }

    { // violation 'There is more than 1 empty line after this line'


        base = 33;
    }

    InputEmptyLineSeparatorPreviousLineEmptiness(
            int base) { // violation 'There is more than 1 empty line after this line'


        this.base = base;
    }

    public InputEmptyLineSeparatorPreviousLineEmptiness() {
    }

    public int increment(int value) { // violation 'There is more than 1 empty line after this line'


        return value * MULTIPLICATOR + 1;
    }

    void foo2() { // ok
    }


    void foo3() { // violation 'There is more than 1 empty line after this line'


    }
}

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
        MULTIPLICATOR = 5; // violation 'There is more than 1 empty line after this line.'


    }

    { // violation 'There is more than 1 empty line after this line.'


        base = 33;
    }

    public InputEmptyLineSeparatorPreviousLineEmptiness(int base) { // violation '.*1 empty line.*'


        this.base = base;
    }

    public InputEmptyLineSeparatorPreviousLineEmptiness() {
    }

    public static int increment(int value) { // violation '.*more than 1 empty line after.*'


        return value * MULTIPLICATOR + 1;
    }

    void foo2() {
    }


    void foo3() { // violation 'There is more than 1 empty line after this line.'


    }
}

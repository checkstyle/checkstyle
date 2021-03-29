package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

/**
 * Config:
 * allowMultipleEmptyLinesInsideClassMembers = false
 */
public class InputEmptyLineSeparatorPreviousLineEmptiness {
    private static final int MULTIPLICATOR;

    private int base;

    static {
        MULTIPLICATOR = 5; // violation


    }

    {


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

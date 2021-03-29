package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

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

    void foo2() {
    }


    void foo3() { // violation


    }
}

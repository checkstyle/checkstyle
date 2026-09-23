package com.openjdk.checkstyle.test.chapternaming.ruleconstants;

// violation first line 'Header is missing'

public class InputConstantsValid {
    public static final int FIRST_CONSTANT = 10;
    public static final int FIRST_CONSTANT1 = 10;
    public static final int CONSTANT_1 = 100;
    protected static final int SECOND_CONSTANT = 100;
    static final int CONSTANT = 1000;

    interface Inter {
        int MAX_ATTEMPTS = 5;
    }

    enum Temp {
        LOW,
        MEDIUM,
        HIGH
    }
}

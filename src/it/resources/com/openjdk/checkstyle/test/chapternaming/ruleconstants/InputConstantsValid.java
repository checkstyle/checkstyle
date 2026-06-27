package com.openjdk.checkstyle.test.chapternaming.ruleconstants;

public class InputConstantsValid {
    public final static int FIRST_CONSTANT = 10;
    protected final static int SECOND_CONSTANT = 100;
    final static int CONSTANT = 1000;

    interface Inter {
        int MAX_ATTEMPTS = 5;
    }

    enum Temp {
        LOW,
        MEDIUM,
        HIGH
    }
}

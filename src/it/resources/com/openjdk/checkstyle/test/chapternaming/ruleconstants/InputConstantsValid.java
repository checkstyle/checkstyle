package com.openjdk.checkstyle.test.chapternaming.ruleconstants;

// violation first line 'Header is missing'

public class InputConstantsValid {
    public final static int FIRST_CONSTANT = 10;
    public final static int FIRST_CONSTANT1 = 10;
    public final static int CONSTANT_1 = 100;
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

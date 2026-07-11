package com.openjdk.checkstyle.test.chapternaming.ruleconstants;

// violation first line 'Header mismatch*'

public class InputConstantsInvalid {
    public final static int FIRST_CONsTANT1 = 10; // violation 'must match pattern'
    protected final static int SECOND_COnSTANT2 = 100; // violation 'must match pattern'
    final static int third_Constant3 = 1000; // violation 'must match pattern'
    private final static int fourth_Const4 = 50; // violation 'must match pattern'
    public final static int log = 10; // violation 'must match pattern'
    protected final static int logger = 50; // violation 'must match pattern'
    final static int loggerMYSELF = 5; // violation 'must match pattern'

    interface Inter {
        int MAx_ATTEMPTS = 5; // violation 'must match pattern'
    }

    enum Temp {
        LOw, // violation 'must match pattern'
        MEDiUM, // violation 'must match pattern'
        HIGh // violation 'must match pattern'
    }

}

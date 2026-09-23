package com.openjdk.checkstyle.test.chapternaming.ruleconstants;

// violation first line 'Header mismatch'

public class InputConstantsInvalid {
    public static final int FIRST_CONsTANT1 = 10; // violation 'must match pattern'
    protected static final int SECOND_COnSTANT2 = 100; // violation 'must match pattern'
    static final int third_Constant3 = 1000; // violation 'must match pattern'
    private static final int fourth_Const4 = 50; // violation 'must match pattern'
    public static final int log = 10; // violation 'must match pattern'
    protected static final int logger = 50; // violation 'must match pattern'
    static final int loggerMYSELF = 5; // violation 'must match pattern'

    interface Inter {
        int MAx_ATTEMPTS = 5; // violation 'must match pattern'
    }

    enum Temp {
        LOw, // violation 'must match pattern'
        MEDiUM, // violation 'must match pattern'
        HIGh // violation 'must match pattern'
    }

}

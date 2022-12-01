/*
AbbreviationAsWordInName
allowedAbbreviations = ORDER, OBSERVATION, UNDERSCORE, TEST


*/


package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

public class InputAbbreviationAsWordInNameType7 {
    // violation below 'Abb.* in name 'HBCK_LOCK_PATH' must contain no .* than '4' .*cap.* let.*.'
    private int HBCK_LOCK_PATH;

    // violation below 'Abb.* in name 'BOOL_VALS' must contain no more than '4' .*cap.* let.*.'
    Boolean[] BOOL_VALS = { false, true };

    void getTEST() {
    } // ok

    void getORDER_OBSERVATION() {} // ok

    // violation below 'Abb.* in name 'getNONE_Test' must contain no more than '4' .*cap.* let.*.'
    void getNONE_Test() {}

    void getCLR_Test() {} // ok

    void getUNDERSCORE() {} // ok

    void getTEST_OBSERVATION() {} // ok

    void getTEST_UNDERSCORE() {} // ok

    void getORDER() {} // ok

    void getOBSERVATION() {} // ok

    void getORDER_UNDERSCORE() {} // ok

    int getCLRTest() { // ok
        // violation below 'Abb.* in name 'LINE_SEP' must contain no more than '4' .*cap.* let.*.'
        int LINE_SEP = 1;
        return LINE_SEP;
    }

    void getNON_ETest() {} // OK


}

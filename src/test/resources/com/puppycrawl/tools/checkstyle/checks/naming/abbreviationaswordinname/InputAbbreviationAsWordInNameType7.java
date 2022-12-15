/*
AbbreviationAsWordInName
allowedAbbreviations = ORDER, OBSERVATION, UNDERSCORE, TEST


*/


package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

public class InputAbbreviationAsWordInNameType7 {
    // violation below 'Abb.* in name 'HBCKL_LOCK_PATH' must contain no .* than '4' .*cap.* let.*.'
    private int HBCKL_LOCK_PATH;

    Boolean[] BOOL_VALS = { false, true };

    void getTEST() {
    } // ok

    void getORDER_OBSERVATION() {} // ok

    void getNONE_Test() {}

    void getCLR_Test() {} // ok

    void getUNDERSCORE() {} // ok

    void getTEST_OBSERVATION() {} // ok

    void getTEST_UNDERSCORE() {} // ok

    void getORDER() {} // ok

    void getOBSERVATION() {} // ok

    void getORDER_UNDERSCORE() {} // ok

    int getCLRTest() { // ok
        int LINE_SEP = 1;
        return LINE_SEP;
    }

    void getNON_ETest() {} // OK


}

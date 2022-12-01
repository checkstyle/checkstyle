/*
AbbreviationAsWordInName
allowedAbbreviations = ORDER, OBSERVATION, UNDERSCORE, TEST


*/


package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

public class InputAbbreviationAsWordInNameType7 {
    // violation below 'Abb.* in name '.*' must contain no more than '4' consecutive cap.* let.*.'
    private int HBCK_LOCK_PATH;

    // violation below 'Abb.* in name '.*' must contain no more than '4' consecutive cap.* let.*.'
    Boolean[] BOOL_VALS = { false, true };

    void getTEST() {
    } // ok

    void getORDER_OBSERVATION() {} // ok

    // violation below 'Abb.* in name '.*' must contain no more than '4' consecutive cap.* let.*.'
    void getNONE_Test() {}

    void getCLR_Test() {} // ok

    void getUNDERSCORE() {} // ok

    void getTEST_OBSERVATION() {} // ok

    void getTEST_UNDERSCORE() {} // ok

    void getORDER() {} // ok

    void getOBSERVATION() {} // ok

    void getORDER_UNDERSCORE() {} // ok

    int getCLRTest() { // ok
        // violation below 'Abb.* in name '.*' must contain no more than '4' conse.* cap.* let.*.'
        int LINE_SEP = 1;
        return LINE_SEP;
    }
}

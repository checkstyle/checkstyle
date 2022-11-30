/*
AbbreviationAsWordInName
allowedAbbreviations = ORDER, OBSERVATION, UNDERSCORE, TEST


*/


package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

public class InputAbbreviationAsWordInNameType7 {
    private int HBCK_LOCK_PATH; // violation

    Boolean[] BOOL_VALS = { false, true }; // violation

    void getTEST() {
    } // ok

    void getORDER_OBSERVATION() {} // ok

    void getNONE_Test() {} // violation

    void getCLR_Test() {} // ok

    void getUNDERSCORE() {} // ok

    void getTEST_OBSERVATION() {} // ok

    void getTEST_UNDERSCORE() {} // ok

    void getORDER() {} // ok

    void getOBSERVATION() {} // ok

    void getORDER_UNDERSCORE() {} // ok

    int getCLRTest() { // ok
        int LINE_SEP = 1; // violation
        return LINE_SEP;
    }
}

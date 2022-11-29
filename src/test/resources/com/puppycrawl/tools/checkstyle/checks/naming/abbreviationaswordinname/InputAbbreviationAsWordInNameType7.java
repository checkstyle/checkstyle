/*
AbbreviationAsWordInName
allowedAbbreviations = ORDER, OBSERVATION, UNDERSCORE, TEST


*/


package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

public class InputAbbreviationAsWordInNameType7 {
    void getORDER_OBSERVATION() {} // ok

    void getNONE_Test() {} // violation

    void getCLR_Test() {} // ok
}

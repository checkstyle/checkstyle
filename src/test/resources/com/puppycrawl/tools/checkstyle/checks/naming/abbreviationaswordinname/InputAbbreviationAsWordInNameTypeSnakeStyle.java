/*
AbbreviationAsWordInName
allowedAbbreviations = ORDER, OBSERVATION, UNDERSCORE, TEST
ignoreOverriddenMethods = (default)true
ignoreStatic = (default)true
tokens = (default)CLASS_DEF,INTERFACE_DEF,ENUM_DEF,ANNOTATION_DEF,ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF,VARIABLE_DEF,METHOD_DEF,PATTERN_VARIABLE_DEF,RECORD_DEF,RECORD_COMPONENT_DEF
ignoreFinal = (default)true
ignoreStaticFinal = (default)true
allowedAbbreviationLength = (default)3

*/


package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

public class InputAbbreviationAsWordInNameTypeSnakeStyle {
    // violation below 'name 'FLAG_IS_FIRST_RUN' must contain no more than '4' .* cap.*.'
    public boolean FLAG_IS_FIRST_RUN = false;

    // violation below 'name 'HYBRID_LOCK_PATH' must contain no more than '4' .* cap.*.'
    private int HYBRID_LOCK_PATH;

    Boolean[] BOOL_VALS = { false, true };

     // violation below 'name '__DEMOS__TESTS_VAR' must contain no more than '4' .* cap.*.'
    private int __DEMOS__TESTS_VAR = 5;

    private int __DEMO__TEST_VAR = 6;

    private int TEST_FAM_23456 = 5;

    // violation below 'name 'TESTING_FAM_23456' must contain no more than '4' .* cap.*.'
    public int TESTING_FAM_23456 = 10;

    private int TEST_23456_FAM = 15;

    // violation below 'name 'TESTING_23456_FAM' must contain no more than '4' .* cap.*.'
    public int TESTING_23456_FAM = 20;

    public int TEST23456 = 30;

    // violation below 'name '_234VIOLATION' must contain no more than '4' .* cap.*.'
    public int _234VIOLATION = 40;

    // violation below 'name 'VIOLATION23456' must contain no more than '4' .* cap.*.'
    public int VIOLATION23456 = 50;

    void getTEST() {
    }

    void getORDER_OBSERVATION() {}

    void getNONE_Test() {}

    void getCLR_Test() {}

    void getUNDERSCORE() {}

    void getTEST_OBSERVATION() {}

    void getTEST_UNDERSCORE() {}

    void getORDER() {}

    void getOBSERVATION() {}

    void getORDER_UNDERSCORE() {}

    int getCLRTest() {
        int LINE_SEP = 1;
        return LINE_SEP;
    }

    void getNON_ETest() {}

    // violation below 'name 'getIsFIRST_Run' must contain no more than '4' .* cap.*.'
    private boolean getIsFIRST_Run() {
        return false;
    }

    // violation below 'name 'getBoolean_VALUES' must contain no more than '4' .* cap.*.'
    private boolean getBoolean_VALUES() {
        return BOOL_VALS[0];
    }
}

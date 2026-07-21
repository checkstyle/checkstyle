/*
AbbreviationAsWordInName
allowedAbbreviationLength = (default)3
allowedAbbreviations = ORDER, OBSERVATION, UNDERSCORE, TEST
ignoreFinal = (default)true
ignoreStatic = (default)true
ignoreStaticFinal = (default)true
ignoreOverriddenMethods = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/


package com.puppycrawl.tools.checkstyle.checks.naming.abbreviationaswordinname;

public class InputAbbreviationAsWordInNameTypeSnakeStyle {
    // violation below 'Abbreviation in name 'FLAG_IS_FIRST_RUN''
    public boolean FLAG_IS_FIRST_RUN = false;

    // violation below 'Abbreviation in name 'HYBRID_LOCK_PATH''
    private int HYBRID_LOCK_PATH;

    Boolean[] BOOL_VALS = { false, true };

     // violation below 'Abbreviation in name '__DEMOS__TESTS_VAR''
    private int __DEMOS__TESTS_VAR = 5;

    private int __DEMO__TEST_VAR = 6;

    private int TEST_FAM_23456 = 5;

    // violation below 'Abbreviation in name 'TESTING_FAM_23456''
    public int TESTING_FAM_23456 = 10;

    private int TEST_23456_FAM = 15;

    // violation below 'Abbreviation in name 'TESTING_23456_FAM''
    public int TESTING_23456_FAM = 20;

    public int TEST23456 = 30;

    // violation below 'Abbreviation in name '_234VIOLATION''
    public int _234VIOLATION = 40;

    // violation below 'Abbreviation in name 'VIOLATION23456''
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

    // violation below 'Abbreviation in name 'getIsFIRST_Run''
    private boolean getIsFIRST_Run() {
        return false;
    }

    // violation below 'Abbreviation in name 'getBoolean_VALUES''
    private boolean getBoolean_VALUES() {
        return BOOL_VALS[0];
    }
}

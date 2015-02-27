package com.puppycrawl.tools.checkstyle.indentation; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 8                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 8                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputSwitchCustom { //indent:0 exp:0
    private static final int ABC1 = 0; //indent:4 exp:4
    private static final int ABC2 = 0; //indent:4 exp:4
    private static final int ABC3 = 0; //indent:4 exp:4

    public int getValue(int value) { //indent:4 exp:4
        switch (value) { //indent:8 exp:8
            case 0: return ABC1; //indent:12 exp:12
            case 1: return ABC2; //indent:12 exp:12
            case 2: return ABC3; //indent:12 exp:12
        } //indent:8 exp:8
        return 0; //indent:8 exp:8
    } //indent:4 exp:4

    public int getValue1(int value) { //indent:4 exp:4
        switch (value) { //indent:8 exp:8
            case 0: //indent:12 exp:12
                return ABC1; //indent:16 exp:16
            case 1: //indent:12 exp:12
                return ABC2; //indent:16 exp:16
            case 2: //indent:12 exp:12
                return ABC3; //indent:16 exp:16
        } //indent:8 exp:8
        return 0; //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0

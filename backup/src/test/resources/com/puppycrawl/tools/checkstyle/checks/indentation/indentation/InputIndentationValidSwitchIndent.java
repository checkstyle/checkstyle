package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * @author  jrichard                                                         //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputIndentationValidSwitchIndent { //indent:0 exp:0

    private static final int CONST = 5; //indent:4 exp:4
    private static final int CONST2 = 2; //indent:4 exp:4
    private static final int CONST3 = 3; //indent:4 exp:4

    /** Creates a new instance of InputIndentationValidSwitchIndent */ //indent:4 exp:4
    public InputIndentationValidSwitchIndent() { //indent:4 exp:4
    } //indent:4 exp:4

    private void method1() { //indent:4 exp:4
        int s = 3; //indent:8 exp:8

        switch (s) { //indent:8 exp:8

            case 4: //indent:12 exp:12
                System.identityHashCode(""); //indent:16 exp:16
                break; //indent:16 exp:16

            case CONST: //indent:12 exp:12
                break; //indent:16 exp:16

            case CONST2: //indent:12 exp:12
            case CONST3: //indent:12 exp:12
                break; //indent:16 exp:16

            default: //indent:12 exp:12
                System.identityHashCode(""); //indent:16 exp:16
                break; //indent:16 exp:16
        } //indent:8 exp:8


        // some people like to add curlies to their cases: //indent:8 exp:8
        switch (s) { //indent:8 exp:8

            case 4: { //indent:12 exp:12
                System.identityHashCode(""); //indent:16 exp:16
                break; //indent:16 exp:16
            } //indent:12 exp:12

            case CONST: //indent:12 exp:12
                break; //indent:16 exp:16

            case CONST2: //indent:12 exp:12
            case CONST3: //indent:12 exp:12
            { //indent:12 exp:12
                System.identityHashCode(""); //indent:16 exp:16
                break; //indent:16 exp:16
            } //indent:12 exp:12

            default: //indent:12 exp:12
                break; //indent:16 exp:16
        } //indent:8 exp:8

        // check broken 'case' lines //indent:8 exp:8
        switch (s) { //indent:8 exp:8

            case  //indent:12 exp:12
                4: { //indent:16 exp:16
                System.identityHashCode(""); //indent:16 exp:16
                break; //indent:16 exp:16
            } //indent:12 exp:12

            case  //indent:12 exp:12
                CONST: //indent:16 exp:16
                break; //indent:16 exp:16

            case CONST2: //indent:12 exp:12
            case  //indent:12 exp:12
                CONST3: //indent:16 exp:16
            { //indent:12 exp:12
                System.identityHashCode(""); //indent:16 exp:16
                break; //indent:16 exp:16
            } //indent:12 exp:12

            default: //indent:12 exp:12
                break; //indent:16 exp:16
        }         //indent:8 exp:8

        switch (s) { //indent:8 exp:8
        } //indent:8 exp:8


        switch (s) { //indent:8 exp:8
            default: //indent:12 exp:12
                System.identityHashCode(""); //indent:16 exp:16
                break; //indent:16 exp:16
        } //indent:8 exp:8

    } //indent:4 exp:4

} //indent:0 exp:0

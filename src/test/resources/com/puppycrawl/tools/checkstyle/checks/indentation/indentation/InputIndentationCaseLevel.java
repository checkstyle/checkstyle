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
public class InputIndentationCaseLevel { //indent:0 exp:0

    /** Creates a new instance of InputIndentationCaseLevel */ //indent:4 exp:4
    public InputIndentationCaseLevel() { //indent:4 exp:4
        int test = 4; //indent:8 exp:8
        switch (test) { //indent:8 exp:8
            case 4: //indent:12 exp:12
                break; //indent:16 exp:16
            case 2: //indent:12 exp:12
                break; //indent:16 exp:16
              default: //indent:14 exp:12 warn
                break; //indent:16 exp:16
        } //indent:8 exp:8


    } //indent:4 exp:4

} //indent:0 exp:0

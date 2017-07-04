package com.puppycrawl.tools.checkstyle.checks.indentation; //indent:0 exp:0

/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 0                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1                                                                         //indent:1 exp:1
public class InputZeroCaseLevel { //indent:0 exp:0
    protected void begin(){ //indent:4 exp:4
        int i=0; //indent:8 exp:8
        switch (i) //indent:8 exp:8
        { //indent:8 exp:8
        case 1: i++; //indent:8 exp:8
        default: i++; //indent:8 exp:8
        } //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0

package com.puppycrawl.tools.checkstyle.checks.indentation; //indent:0 exp:0


/**                                                                           //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = true                                                //indent:1 exp:1
 * lineWrappingIndentation = 8                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 8                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1                                                                   //indent:1 exp:1
public class InputMethodCStyle { //indent:0 exp:0
    public InputMethodCStyle(int appleCount, //indent:4 exp:4
                             int bananaCount, //indent:29 exp:12 warn
                             int pearsCount) { //indent:29 exp:12 warn
    } //indent:4 exp:4

    public InputMethodCStyle(String appleCount, //indent:4 exp:4
            int bananaCount, //indent:12 exp:12
            int pearsCount) { //indent:12 exp:12
    }//indent:4 exp:4
} //indent:0 exp:0

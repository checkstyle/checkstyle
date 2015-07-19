package com.puppycrawl.tools.checkstyle.indentation; //indent:0 exp:0

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
 */                                                                           //indent:1 exp:1
public class InputInvalidLabelWithWhileLoopIndent { //indent:0 exp:0

    public InputInvalidLabelWithWhileLoopIndent() { //indent:4 exp:4
         LOOP://indent:9 exp:8 warn
         while (true) { //indent:9 exp:8 warn
            break LOOP; //indent:12 exp:13 warn
        } //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0

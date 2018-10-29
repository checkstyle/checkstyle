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
public class InputIndentationValidLabelIndent { //indent:0 exp:0

    /** Creates a new instance of InputIndentationValidLabelIndent */ //indent:4 exp:4
    public InputIndentationValidLabelIndent() { //indent:4 exp:4
        boolean test = true; //indent:8 exp:8

        while (test) { //indent:8 exp:8
        label: //indent:8 exp:8,12
            System.identityHashCode("label test"); //indent:12 exp:12,16

            if (test) { //indent:12 exp:12
            unusedLabel: //indent:12 exp:12
                System.identityHashCode("more testing"); //indent:16 exp:16,20
            } //indent:12 exp:12

        } //indent:8 exp:8
    label2: //indent:4 exp:4,8
        System.identityHashCode("toplevel"); //indent:8 exp:8,12
    } //indent:4 exp:4

} //indent:0 exp:0

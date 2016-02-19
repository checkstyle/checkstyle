package com.puppycrawl.tools.checkstyle.checks.indentation; //indent:0 exp:0

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
 * throwsIndent = 8                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1
public class InputInvalidThrowsIndent { //indent:0 exp:0

    public InputInvalidThrowsIndent() //indent:4 exp:4
    { //indent:4 exp:4
    } //indent:4 exp:4

    // This should pass for our reconfigured throwsIndent test. //indent:4 exp:4
    private void myFunc() //indent:4 exp:4
            throws Exception //indent:12 exp:>=12
    { //indent:4 exp:4
    } //indent:4 exp:4

    // This is the out of the box default configuration, but should fail //indent:4 exp:4
    // for our reconfigured test. //indent:4 exp:4
    private void myFunc2() //indent:4 exp:4
            throws Exception //indent:12 exp:>=12
    { //indent:4 exp:4
    } //indent:4 exp:4

    private void myFunc3() //indent:4 exp:4
            throws //indent:12 exp:>=12
            Exception //indent:12 exp:>=12
    { //indent:4 exp:4
    } //indent:4 exp:4

    private void myFunc4() throws //indent:4 exp:4
                Exception //indent:16 exp:>=12
    { //indent:4 exp:4
    } //indent:4 exp:4
} //indent:0 exp:0

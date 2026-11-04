package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;      //indent:0 exp:0

/**                                                                          //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:  //indent:1 exp:1
 *                                                                           //indent:1 exp:1
 * arrayInitIndent = 4                                                       //indent:1 exp:1
 * basicOffset = 4                                                           //indent:1 exp:1
 * braceAdjustment = 0                                                       //indent:1 exp:1
 * caseIndent = 4                                                            //indent:1 exp:1
 * forceStrictCondition = false                                              //indent:1 exp:1
 * lineWrappingIndentation = 8                                               //indent:1 exp:1
 * tabWidth = 4                                                              //indent:1 exp:1
 * throwsIndent = 4                                                          //indent:1 exp:1
 */                                                                          //indent:1 exp:1
public class InputIndentationAnnotationFieldConstants {                      //indent:0 exp:0
    public @interface InnerInterface {                                       //indent:4 exp:4
        int CONSTANT = 1;                                                    //indent:8 exp:8
        String NAME = "name";                                                //indent:8 exp:8
        int[] VALUES = {1, 2};                                               //indent:8 exp:8

        String value() default NAME;                                         //indent:8 exp:8
    }                                                                        //indent:4 exp:4
}                                                                            //indent:0 exp:0

@interface InputIndentationAnnotationFieldConstantsTopLevel {                //indent:0 exp:0
    int CONSTANT = 1;                                                        //indent:4 exp:4

    int value() default CONSTANT;                                            //indent:4 exp:4
}                                                                            //indent:0 exp:0

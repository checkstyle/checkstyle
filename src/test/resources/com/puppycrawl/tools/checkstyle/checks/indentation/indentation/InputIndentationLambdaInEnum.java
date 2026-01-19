/*                                                                      //indent:0 exp:0
 * Config:                                                              //indent:1 exp:1
 * This test-input is intended to be checked using following            //indent:1 exp:1
 * configuration:                                                       //indent:1 exp:1
 *                                                                      //indent:1 exp:1
 * basicOffset = 4                                                      //indent:1 exp:1
 * braceAdjustment = 0                                                  //indent:1 exp:1
 * caseIndent = 4                                                       //indent:1 exp:1
 * throwsIndent = 4                                                     //indent:1 exp:1
 * arrayInitIndent = 4                                                  //indent:1 exp:1
 * lineWrappingIndentation = 4                                          //indent:1 exp:1
 * forceStrictCondition = false                                         //indent:1 exp:1
 * tabWidth = 8                                                         //indent:1 exp:1
 */                                                                     //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.util.function.Function;                                     //indent:0 exp:0

/**                                                                     //indent:0 exp:0
 * Test Lambda in Enum causes error loop.                               //indent:1 exp:1
 */                                                                     //indent:1 exp:1
public enum InputIndentationLambdaInEnum {                              //indent:0 exp:0

    ENUM_VALUE(                                                         //indent:4 exp:4
            v -> v,                                                     //indent:12 exp:12
            new Object()                                                //indent:12 exp:12
    );                                                                  //indent:4 exp:4

    private final Function<Object, Object> function;                    //indent:4 exp:4
    private final Object object;                                        //indent:4 exp:4

    InputIndentationLambdaInEnum(Function<Object, Object> function,     //indent:4 exp:4
                                  Object object) {                      //indent:34 exp:34
        this.function = function;                                       //indent:8 exp:8
        this.object = object;                                           //indent:8 exp:8
    }                                                                   //indent:4 exp:4
}                                                                       //indent:0 exp:0

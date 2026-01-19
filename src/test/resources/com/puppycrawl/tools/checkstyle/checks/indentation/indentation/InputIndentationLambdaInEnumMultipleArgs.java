/*                                                                              //indent:0 exp:0
 * Config:                                                                      //indent:1 exp:1
 * This test-input is intended to be checked using following configuration:     //indent:1 exp:1
 *                                                                              //indent:1 exp:1
 * basicOffset = 4                                                              //indent:1 exp:1
 * braceAdjustment = 0                                                          //indent:1 exp:1
 * caseIndent = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                             //indent:1 exp:1
 * arrayInitIndent = 4                                                          //indent:1 exp:1
 * lineWrappingIndentation = 4                                                  //indent:1 exp:1
 * forceStrictCondition = false                                                 //indent:1 exp:1
 * tabWidth = 8                                                                 //indent:1 exp:1
 */                                                                             //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;         //indent:0 exp:0

import java.util.function.Consumer;                                             //indent:0 exp:0
import java.util.function.Function;                                             //indent:0 exp:0

/**                                                                             //indent:0 exp:0
 * Test Lambda in Enum with multiple arguments.                                 //indent:1 exp:1
 */                                                                             //indent:1 exp:1
public enum InputIndentationLambdaInEnumMultipleArgs {                          //indent:0 exp:0

    NAME(                                                                       //indent:4 exp:4
        String::getClass,                                                       //indent:8 exp:8
        any -> {}                                                               //indent:8 exp:8
    );                                                                          //indent:4 exp:4

    InputIndentationLambdaInEnumMultipleArgs(                                   //indent:4 exp:4
            Function<String, Class<?>> function,                                //indent:12 exp:12
            Consumer<String> consumer) {                                        //indent:12 exp:12
    }                                                                           //indent:4 exp:4
}                                                                               //indent:0 exp:0

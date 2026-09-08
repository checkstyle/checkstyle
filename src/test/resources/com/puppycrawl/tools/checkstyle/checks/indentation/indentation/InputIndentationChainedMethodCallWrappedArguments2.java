/* Config:                                                                  //indent:0 exp:0
 * This test-input is intended to be checked using following configuration: //indent:1 exp:1
 *                                                                          //indent:1 exp:1
 * arrayInitIndent = 4                                                      //indent:1 exp:1
 * basicOffset = 4                                                          //indent:1 exp:1
 * braceAdjustment = 0                                                      //indent:1 exp:1
 * caseIndent = 4                                                           //indent:1 exp:1
 * forceStrictCondition = true                                              //indent:1 exp:1
 * lineWrappingIndentation = 4                                              //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * throwsIndent = 4                                                         //indent:1 exp:1
 *                                                                          //indent:1 exp:1
 */                                                                         //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;     //indent:0 exp:0

class InputIndentationChainedMethodCallWrappedArguments2 {                  //indent:0 exp:0

    void argumentsNotWrappedFarEnough() {                                   //indent:4 exp:4
        new Builder()                                                       //indent:8 exp:8
            .foo(1                                                          //indent:12 exp:12
            + 1)                                                            //indent:12 exp:16 warn
            .foo(1                                                          //indent:12 exp:12
                + 1);                                                       //indent:16 exp:16
    }                                                                       //indent:4 exp:4

    private static final class Builder {                                    //indent:4 exp:4

        Builder foo(int number) {                                           //indent:8 exp:8
            return this;                                                    //indent:12 exp:12
        }                                                                   //indent:8 exp:8
    }                                                                       //indent:4 exp:4
}                                                                           //indent:0 exp:0

/* Config:                                                                  //indent:0 exp:0
 * This test-input is intended to be checked using following configuration: //indent:1 exp:1
 *                                                                          //indent:1 exp:1
 * lineWrappingIndentation = 8                                              //indent:1 exp:1
 * forceStrictCondition = true                                              //indent:1 exp:1
 *                                                                          //indent:1 exp:1
 */                                                                         //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;     //indent:0 exp:0

public class InputIndentationChainedMethodCallsWithWrappedArguments {       //indent:0 exp:0
    public static void main(String[] args) {                                //indent:4 exp:4
        new Chainable()                                                     //indent:8 exp:8
                .foo(1                                                      //indent:16 exp:16
                        + 1)                                                //indent:24 exp:24
                .foo(1                                                      //indent:16 exp:16
                        + 1)                                                //indent:24 exp:24
                .foo(1 + 1);                                                //indent:16 exp:16

        new Chainable()                                                     //indent:8 exp:8
                .foo(1                                                      //indent:16 exp:16
                        + 1)                                                //indent:24 exp:24
                .foo(1                                                      //indent:16 exp:16
                        + 1)                                                //indent:24 exp:24
                .foo(1                                                      //indent:16 exp:16
                        + 1);                                               //indent:24 exp:24
    }                                                                       //indent:4 exp:4

    static class Chainable {                                                //indent:4 exp:4
        Chainable foo(int x) {                                              //indent:8 exp:8
            return this;                                                    //indent:12 exp:12
        }                                                                   //indent:8 exp:8
    }                                                                       //indent:4 exp:4
}                                                                           //indent:0 exp:0

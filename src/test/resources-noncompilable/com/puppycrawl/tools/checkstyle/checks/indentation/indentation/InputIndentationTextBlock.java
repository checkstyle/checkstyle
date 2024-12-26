//non-compiled with javac: Compilable with Java17                                   //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

/* Config:                                                                    //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * forceStrictCondition = true                                                //indent:1 exp:1
 */                                                                           //indent:1 exp:1


public class InputIndentationTextBlock {       //indent:0 exp:0
    private static final String EXAMPLE = """  //indent:4 exp:4
        Example string""";                     //indent:8 exp:8

    private static final String GO = """       //indent:4 exp:4
        GO                                     //indent:8 exp:8
        """;                                   //indent:8 exp:8
}                                              //indent:0 exp:0

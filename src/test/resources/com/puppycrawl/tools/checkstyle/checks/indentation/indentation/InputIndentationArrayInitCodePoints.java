/* Config:                                                      //indent:0 exp:0
 * arrayInitIndent = 4                                          //indent:1 exp:1
 * basicOffset = 4                                              //indent:1 exp:1
 * braceAdjustment = 0                                          //indent:1 exp:1
 * caseIndent = 4                                               //indent:1 exp:1
 * forceStrictCondition = false                                 //indent:1 exp:1
 * lineWrappingIndentation = 4                                  //indent:1 exp:1
 * tabWidth = 4                                                 //indent:1 exp:1
 * throwsIndent = 4                                             //indent:1 exp:1
 */                                                             //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationArrayInitCodePoints {              //indent:0 exp:0
    int[] a = { 1,                                              //indent:4 exp:4
                2,                                              //indent:16 exp:16
    };                                                          //indent:4 exp:4

    int[] 𝒜 = { 1,                                              //indent:4 exp:4
                2,                                              //indent:16 exp:16
    };                                                          //indent:4 exp:4

    int[] b /* 😀 */ = { 1,                                      //indent:4 exp:4
                        2,                                      //indent:24 exp:24
    };                                                          //indent:4 exp:4

    int[] 𝒞𝒟 = { 1,                                             //indent:4 exp:4
                 2,                                             //indent:17 exp:17
    };                                                          //indent:4 exp:4

    int[] 𝒵 = { 1,                                              //indent:4 exp:4
               2,                                               //indent:15 exp:8,16,18 warn
    };                                                          //indent:4 exp:4
}                                                               //indent:0 exp:0

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;      //indent:0 exp:0

/* Config:                                                                   //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:  //indent:1 exp:1
 * tabWidth = 4                                                              //indent:1 exp:1
 * basicOffset = 4                                                           //indent:1 exp:1
 * braceAdjustment = 0                                                       //indent:1 exp:1
 * caseIndent = 4                                                            //indent:1 exp:1
 * lineWrappingIndentation = 4                                               //indent:1 exp:1
 */                                                                          //indent:1 exp:1

public class InputIndentationYieldSwitchExpressionWrapped {                  //indent:0 exp:0
    int bar(int i) {                                                         //indent:4 exp:4
        return switch (i) {                                                  //indent:8 exp:8
            case 0 -> {                                                      //indent:12 exp:12
                yield                                                        //indent:16 exp:16
                    switch (i) {                                             //indent:20 exp:20
                        case 0 -> 0;                                         //indent:24 exp:24
                        default -> 1;                                        //indent:24 exp:24
                    };                                                       //indent:20 exp:20
            }                                                                //indent:12 exp:12
            default -> 1;                                                    //indent:12 exp:12
        };                                                                   //indent:8 exp:8
    }                                                                        //indent:4 exp:4
}                                                                            //indent:0 exp:0


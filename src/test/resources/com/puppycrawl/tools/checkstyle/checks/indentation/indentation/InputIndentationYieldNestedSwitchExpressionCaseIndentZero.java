package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

/*                                                                         //indent:0 exp:0
 * Config:                                                                 //indent:1 exp:1
 *                                                                         //indent:1 exp:1
 * basicOffset = 4                                                         //indent:1 exp:1
 * braceAdjustment = 0                                                     //indent:1 exp:1
 * caseIndent = 0                                                          //indent:1 exp:1
 * throwsIndent = 4                                                        //indent:1 exp:1
 * arrayInitIndent = 4                                                     //indent:1 exp:1
 * lineWrappingIndentation = 4                                             //indent:1 exp:1
 * forceStrictCondition = false                                            //indent:1 exp:1
 */                                                                        //indent:1 exp:1
public class InputIndentationYieldNestedSwitchExpressionCaseIndentZero {   //indent:0 exp:0

    int test(int i) {                                                      //indent:4 exp:4
        return switch (i) {                                                //indent:8 exp:8
        case 42:                                                           //indent:8 exp:8
            if (i == 0) {                                                  //indent:12 exp:12
                yield 41 + switch (0) {                                    //indent:16 exp:16
                    case 0 -> 1;                                           //indent:20 exp:16 warn
                    default -> -1;                                         //indent:20 exp:20
                };                                                         //indent:16 exp:16
            }                                                              //indent:12 exp:12
            yield 43;                                                      //indent:12 exp:12
        default:                                                           //indent:8 exp:8
            yield -1;                                                      //indent:12 exp:12
        };                                                                 //indent:8 exp:8
    }                                                                      //indent:4 exp:4
}                                                                          //indent:0 exp:0

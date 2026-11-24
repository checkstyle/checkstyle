/*
Indentation
arrayInitIndent = (default)4
basicOffset = (default)4
braceAdjustment = (default)0
caseIndent = 0
forceStrictCondition = (default)false
lineWrappingIndentation = (default)4
tabWidth = 4
throwsIndent = (default)4

*/
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationYieldNestedSwitchExpressionCaseIndentZero {   //indent:0 exp:0

    int test(int i) {                                                      //indent:4 exp:4
        return switch (i) {                                                //indent:8 exp:8
        case 42:                                                           //indent:8 exp:8
            if (i == 0) {                                                  //indent:12 exp:12
                yield 41 + switch (0) {                                    //indent:16 exp:16
                    // violation below ''case' child has incorrect indentation level 20, expected level should be 16.'
                    case 0 -> 1;                                           //indent:20 exp:16 warn
                    // violation below ''case' child has incorrect indentation level 20, expected level should be 16.'
                    default -> -1;                                         //indent:20 exp:20
                };                                                         //indent:16 exp:16
            }                                                              //indent:12 exp:12
            yield 43;                                                      //indent:12 exp:12
        default:                                                           //indent:8 exp:8
            yield -1;                                                      //indent:12 exp:12
        };                                                                 //indent:8 exp:8
    }                                                                      //indent:4 exp:4
}                                                                          //indent:0 exp:0

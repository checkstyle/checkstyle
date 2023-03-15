//non-compiled with javac: Compilable with Java14                                   //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;             //indent:0 exp:0
                                                                                  //indent:82 exp:82
/* Config:                                                                          //indent:0 exp:0
 *                                                                                  //indent:1 exp:1
 * basicOffset = 4                                                                  //indent:1 exp:1
 * braceAdjustment = 0                                                              //indent:1 exp:1
 * caseIndent = 4                                                                   //indent:1 exp:1
 * throwsIndent = 4                                                                 //indent:1 exp:1
 * arrayInitIndent = 4                                                              //indent:1 exp:1
 * lineWrappingIndentation = 4                                                      //indent:1 exp:1
 * forceStrictCondition = false                                                     //indent:1 exp:1
 */                                                                                 //indent:1 exp:1
public class InputIndentationCheckSwitchExpressionCorrect {                         //indent:0 exp:0
    MathOperation2 tooManyParens(int k) {                                           //indent:4 exp:4
        return switch (k) {                                                         //indent:8 exp:8
            case 1 -> {                                                           //indent:12 exp:12
                MathOperation2 case5 = (a, b) -> (a + b);                         //indent:16 exp:16
                yield case5;                                                      //indent:16 exp:16
            }                                                                     //indent:12 exp:12
            case (2) -> {                                                         //indent:12 exp:12
                MathOperation2 case6 = (int a, int b) -> (a + b);                 //indent:16 exp:16
                yield case6;                                                      //indent:16 exp:16
            }                                                                     //indent:12 exp:12
            case 3 -> {                                                           //indent:12 exp:12
                MathOperation2 case7 = (int a, int b) -> {                        //indent:16 exp:16
                    return (a + b);                                               //indent:20 exp:20
                };                                                                //indent:16 exp:16
                yield (case7);                                                    //indent:16 exp:16
            }                                                                     //indent:12 exp:12
            default -> {                                                          //indent:12 exp:12
                MathOperation2 case8 = (int x, int y) -> {                        //indent:16 exp:16
                    return (x + y);                                               //indent:20 exp:20
                };                                                                //indent:16 exp:16
                yield case8;                                                      //indent:16 exp:16
            }                                                                     //indent:12 exp:12
        };                                                                          //indent:8 exp:8
    }                                                                               //indent:4 exp:4
                                                                                  //indent:82 exp:82
    MathOperation2 tooManyParens2(int k) {                                          //indent:4 exp:4
        switch (k) {                                                                //indent:8 exp:8
            case 1 -> {                                                           //indent:12 exp:12
                MathOperation2 case5 = (a, b) -> (a + b);                         //indent:16 exp:16
            }                                                                     //indent:12 exp:12
            case (2) -> {                                                         //indent:12 exp:12
                MathOperation2 case6 = (int a, int b) -> (a + b);                 //indent:16 exp:16
            }                                                                     //indent:12 exp:12
            case 3 -> {                                                           //indent:12 exp:12
                MathOperation2 case7 = (int a, int b) -> {                        //indent:16 exp:16
                    return (a + b + 2);                                           //indent:20 exp:20
                };                                                                //indent:16 exp:16
            }                                                                     //indent:12 exp:12
            default -> {                                                          //indent:12 exp:12
                MathOperation2 case8 = (int x, int y) -> {                        //indent:16 exp:16
                    return (x + y);                                               //indent:20 exp:20
                };                                                                //indent:16 exp:16
            }                                                                     //indent:12 exp:12
        }                                                                           //indent:8 exp:8
        return (a, b) -> 0;                                                         //indent:8 exp:8
    }                                                                               //indent:4 exp:4
                                                                                  //indent:82 exp:82
    interface MathOperation2 {                                                      //indent:4 exp:4
        int operation(int a, int b);                                                //indent:8 exp:8
    }                                                                               //indent:4 exp:4
}                                                                                   //indent:0 exp:0
                                                                                  //indent:82 exp:82

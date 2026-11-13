// non-compiled with javac: Compilable with Java25                          //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;     //indent:0 exp:0


/* Config:                                                                  //indent:0 exp:0
 * basicOffset = 4                                                          //indent:1 exp:1
 * braceAdjustment = 0                                                      //indent:1 exp:1
 * caseIndent = 4                                                           //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * throwsIndent = 8                                                         //indent:1 exp:1
 * forceStrictCondition = true                                              //indent:1 exp:1
 */                                                                         //indent:1 exp:1
public class InputIndentationRecordPatternClosingParen {                    //indent:0 exp:0
    record ColoredPoint(boolean p, int x, int c) { }                        //indent:4 exp:4
    record Rectangle(ColoredPoint upperLeft, ColoredPoint lowerRight) { }   //indent:4 exp:4

    void testClosingParentheses(Object obj) {                               //indent:4 exp:4
        if (obj instanceof                                                  //indent:8 exp:8
            ColoredPoint(_, _, _                                            //indent:12 exp:12
            )) {}                                                           //indent:12 exp:12

        if (obj instanceof                                                  //indent:8 exp:8
            ColoredPoint(_, _, _                                            //indent:12 exp:12
            )                                                               //indent:12 exp:12
        ) {}                                                                //indent:8 exp:8

        if (obj instanceof ColoredPoint(_, _, _                             //indent:8 exp:8
            )) {}                                                           //indent:12 exp:12

        if (obj instanceof                                                  //indent:8 exp:8
            Rectangle(                                                      //indent:12 exp:12
            ColoredPoint(_, _, _                                            //indent:12 exp:12
            ),                                                              //indent:12 exp:12
            ColoredPoint(_, _, _                                            //indent:12 exp:12
            )                                                               //indent:12 exp:12
            )                                                               //indent:12 exp:12
        ) {}                                                                //indent:8 exp:8

        if (obj instanceof                                                  //indent:8 exp:8
            ColoredPoint(_, _, _                                            //indent:12 exp:12
                )) {}                                                       //indent:16 exp:12 warn
    }                                                                       //indent:4 exp:4
}                                                                           //indent:0 exp:0

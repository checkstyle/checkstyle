//non-compiled with javac: Compilable with Java15                                   //indent:0 exp:0
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
public class InputIndentationCheckSwitchExpressionNewLine {                         //indent:0 exp:0
                                                                                  //indent:82 exp:82
    private B map(A a) {                                                            //indent:4 exp:4
        return switch (a) {                                                         //indent:8 exp:8
            case one, two,                                                        //indent:12 exp:12
                three, four                                                       //indent:16 exp:16
                -> B.one;                                                         //indent:16 exp:16
            default                                                               //indent:12 exp:12
                -> B.two;                                                         //indent:16 exp:16
        };                                                                          //indent:8 exp:8
    }                                                                               //indent:4 exp:4
                                                                                  //indent:82 exp:82
    private A map(B a) {                                                            //indent:4 exp:4
        return switch (a) {                                                         //indent:8 exp:8
            case one, two,                                                        //indent:12 exp:12
                three, four                                                       //indent:16 exp:16
            -> A.one;                                                        //indent:12 exp:16 warn
            default                                                               //indent:12 exp:12
            -> A.two;                                                        //indent:12 exp:16 warn
        };                                                                          //indent:8 exp:8
    }                                                                               //indent:4 exp:4
                                                                                  //indent:82 exp:82
    enum A {                                                                        //indent:4 exp:4
        one, two, three, four, five                                                 //indent:8 exp:8
    }                                                                               //indent:4 exp:4
                                                                                  //indent:82 exp:82
    enum B {                                                                        //indent:4 exp:4
        one, two, three, four, five                                                 //indent:8 exp:8
    }                                                                               //indent:4 exp:4
}                                                                                   //indent:0 exp:0
                                                                                  //indent:82 exp:82

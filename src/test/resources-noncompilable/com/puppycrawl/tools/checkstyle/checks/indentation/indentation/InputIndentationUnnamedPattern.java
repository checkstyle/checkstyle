// non-compiled with javac: Compilable with Java25                          //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;     //indent:0 exp:0


/* Config:                                                                  //indent:0 exp:0
 * basicOffset = 4                                                          //indent:1 exp:1
 * braceAdjustment = 0                                                      //indent:1 exp:1
 * caseIndent = 4                                                           //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * throwsIndent = 8                                                         //indent:1 exp:1
 * forceStrictCondition = false                                             //indent:1 exp:1
 */                                                                         //indent:1 exp:1
public class InputIndentationUnnamedPattern {                               //indent:0 exp:0
    record ColoredPoint(boolean p) { }                                      //indent:4 exp:4

    void test(Object obj) {                                                 //indent:4 exp:4
        if (obj instanceof ColoredPoint(_)) {                               //indent:8 exp:8
        }                                                                   //indent:8 exp:8
        if (obj instanceof ColoredPoint(                                    //indent:8 exp:8
                _)) {                                                       //indent:16 exp:>=12
        }                                                                   //indent:8 exp:8
        if (obj instanceof ColoredPoint(                                    //indent:8 exp:8
                        _)) {                                               //indent:24 exp:>=12
        }                                                                   //indent:8 exp:8
        if (obj instanceof ColoredPoint(                                    //indent:8 exp:8
        _)) {                                                               //indent:8 exp:>=12 warn
        }                                                                   //indent:8 exp:8
        if (obj instanceof ColoredPoint(                                    //indent:8 exp:8
_)) {                                                                       //indent:0 exp:>=12 warn
        }                                                                   //indent:8 exp:8
    }                                                                       //indent:4 exp:4
}                                                                           //indent:0 exp:0

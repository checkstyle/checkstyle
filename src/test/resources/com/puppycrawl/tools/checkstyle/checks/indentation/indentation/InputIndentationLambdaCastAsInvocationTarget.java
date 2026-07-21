//                                                                              //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;         //indent:0 exp:0

/* Config:                                                                      //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:     //indent:1 exp:1
 * basicOffset = 4                                                              //indent:1 exp:1
 * braceAdjustment = 0                                                          //indent:1 exp:1
 * caseIndent = 4                                                               //indent:1 exp:1
 * tabWidth = 4                                                                 //indent:1 exp:1
 * lineWrappingIndentation = 4                                                  //indent:1 exp:1
 * forceStrictCondition = false                                                 //indent:1 exp:1
 */                                                                             //indent:1 exp:1
public class InputIndentationLambdaCastAsInvocationTarget {                     //indent:0 exp:0
    void method1() {                                                            //indent:4 exp:4
        ((Runnable) (() -> {                                                    //indent:8 exp:8
                        int x = 1; }))                                          //indent:24 exp:12,20 warn
                .run();                                                         //indent:16 exp:16
    }                                                                           //indent:4 exp:4

    void method2() {                                                            //indent:4 exp:4
        ((Runnable) (() -> {                                                    //indent:8 exp:8
            int x = 1;                                                          //indent:12 exp:12
        }))                                                                     //indent:8 exp:8
                .run();                                                         //indent:16 exp:16
    }                                                                           //indent:4 exp:4

    void method3() {                                                            //indent:4 exp:4
        ((Runnable) (() -> {                                                    //indent:8 exp:8
                        System.out.println(this); }))                           //indent:24 exp:12,20 warn
                .run();                                                         //indent:16 exp:16
    }                                                                           //indent:4 exp:4
}                                                                               //indent:0 exp:0

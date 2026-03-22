package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;    //indent:0 exp:0

/**                                                                        //indent:0 exp:0
 * This test input is intended to be checked using following configuration //indent:1 exp:1
 *                                                                         //indent:1 exp:1
 * basicOffset = 4                                                         //indent:1 exp:1
 * braceAdjustment = 0                                                     //indent:1 exp:1
 * caseIndent = 4                                                          //indent:1 exp:1
 * forceStrictCondition = false                                            //indent:1 exp:1
 * lineWrappingIndentation = 4                                             //indent:1 exp:1
 * tabWidth = 4                                                            //indent:1 exp:1
 * throwsIndent = 4                                                        //indent:1 exp:1
 */                                                                        //indent:1 exp:1
public class InputIndentationSynchronizedExprViolation {                   //indent:0 exp:0
    private final Object lock = new Object();                              //indent:4 exp:4
    void method() {                                                        //indent:4 exp:4
        synchronized (                                                     //indent:8 exp:8
lock                                                                       //indent:0 exp:12 warn
        ) {                                                                //indent:8 exp:8
            System.out.println("test");                                    //indent:12 exp:12
        }                                                                  //indent:8 exp:8
    }                                                                      //indent:4 exp:4
}                                                                          //indent:0 exp:0

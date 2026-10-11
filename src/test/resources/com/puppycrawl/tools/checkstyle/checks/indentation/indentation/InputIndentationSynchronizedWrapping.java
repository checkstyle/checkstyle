package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;    //indent:0 exp:0

/**                                                                         //indent:0 exp:0
 * Test for checkWrappingIndentation in SynchronizedHandler.                //indent:1 exp:1
 * arrayInitIndent = 4                                                      //indent:1 exp:1
 * basicOffset = 4                                                          //indent:1 exp:1
 * braceAdjustment = 0                                                      //indent:1 exp:1
 * caseIndent = 4                                                           //indent:1 exp:1
 * forceStrictCondition = false                                             //indent:1 exp:1
 * lineWrappingIndentation = 8                                              //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * throwsIndent = 4                                                         //indent:1 exp:1
 */                                                                         //indent:1 exp:1
public class InputIndentationSynchronizedWrapping {                         //indent:0 exp:0
    Object lock = new Object();                                             //indent:4 exp:4

    void method() {                                                         //indent:4 exp:4
        synchronized (lock) {                                               //indent:8 exp:8
            System.out.println("valid");                                    //indent:12 exp:12
        }                                                                   //indent:8 exp:8
        synchronized (lock                                                  //indent:8 exp:8
            .getClass()) {                                                  //indent:12 exp:16 warn
            System.out.println("invalid wrapping");                         //indent:12 exp:12
        }                                                                   //indent:8 exp:8
    }                                                                       //indent:4 exp:4
}                                                                           //indent:0 exp:0

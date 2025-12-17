package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationSynchronizedExprViolation {                //indent:0 exp:0
    private final Object lock = new Object();                           //indent:4 exp:4
    void method() {                                                     //indent:4 exp:4
        synchronized (                                                  //indent:8 exp:8
lock                                                                    //indent:0 exp:12 warn
        ) {                                                             //indent:8 exp:8
            System.out.println("test");                                 //indent:12 exp:12
        }                                                               //indent:8 exp:8
    }                                                                   //indent:4 exp:4
}                                                                       //indent:0 exp:0

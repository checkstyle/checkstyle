package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;    //indent:0 exp:0

import java.lang.Thread;                                               	//indent:0 exp:0

public class InputIndentationStrictCondition {                             //indent:0 exp:0
    void method(Thread foo) {                                          	//indent:4 exp:4
        method(                                                        	//indent:8 exp:8
                new Thread() {                                         	//indent:16 exp:16
                        public void run() {                                //indent:24 exp:24
                            }                                         	 //indent:28 exp:16,20,24 warn
                    }                                                      //indent:20 exp:20
        );                                                                 //indent:8 exp:8
        }                                                             	 //indent:8 exp:4 warn
    }                                                                 	 //indent:4 exp:0 warn

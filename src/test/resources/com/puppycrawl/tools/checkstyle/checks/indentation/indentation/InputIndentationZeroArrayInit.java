package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationZeroArrayInit { //indent:0 exp:0
    interface MyInterface { //indent:4 exp:4
        @interface SomeAnnotation { String[] values(); } //indent:8 exp:8
        @SomeAnnotation(values = { //indent:8 exp:8
           "Hello"//indent:11 exp:8,12,35,37 warn
        }) //indent:8 exp:8
        void works(); //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0

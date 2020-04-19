package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationoddLineWrappingAndArrayInit { //indent:0 exp:0
    interface MyInterface { //indent:4 exp:4
        @interface SomeAnnotation { String[] values(); } //indent:8 exp:8
        interface Info { //indent:8 exp:8
            String A = "a"; //indent:12 exp:12
        } //indent:8 exp:8
        @MyInterface.SomeAnnotation(values = {       //indent:8 exp:8
                MyInterface.Info.A, //indent:16 exp:11,17,20,26,29,35,38,44,53,54 warn
        } //indent:8 exp:8
        ) //indent:8 exp:8
        void works(); //indent:8 exp:8
    } //indent:4 exp:4
} //indent:0 exp:0

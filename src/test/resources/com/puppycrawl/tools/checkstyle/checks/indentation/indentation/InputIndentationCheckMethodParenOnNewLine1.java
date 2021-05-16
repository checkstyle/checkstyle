package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

/* Config: default */ //indent:0 exp:0

public class InputIndentationCheckMethodParenOnNewLine1 { //indent:0 exp:0
    void //indent:4 exp:4
        method ( //indent:8 exp:8
    ) //indent:4 exp:4
    { //indent:4 exp:4
        int b = //indent:8 exp:8
         2 //indent:9 exp:12 warn
            * //indent:12 exp:12
            3; //indent:12 exp:12
    } //indent:4 exp:4
    void //indent:4 exp:4
        methodTest ( //indent:8 exp:8
       int a //indent:7 exp:8 warn
        ) //indent:8 exp:4 warn
    {} //indent:4 exp:4
} //indent:0 exp:0

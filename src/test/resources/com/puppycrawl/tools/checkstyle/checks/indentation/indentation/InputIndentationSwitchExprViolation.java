package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationSwitchExprViolation {                      //indent:0 exp:0
    void method() {                                                     //indent:4 exp:4
        int x = 1;                                                      //indent:8 exp:8
        switch (                                                        //indent:8 exp:8
x                                                                       //indent:0 exp:8 warn
        ) {                                                             //indent:8 exp:8
            case 1:                                                     //indent:12 exp:12
                break;                                                  //indent:16 exp:16
        }                                                               //indent:8 exp:8
    }                                                                   //indent:4 exp:4
}                                                                       //indent:0 exp:0

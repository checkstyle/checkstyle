package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;         //indent:0 exp:0

public class InputIndentationNewMultilineStatement {                            //indent:0 exp:0
    void method() {                                                             //indent:4 exp:4
        new String("VeryLoooooooooo"                                            //indent:8 exp:8
+ "oongString");                                                                //indent:0 exp:12 warn
        new String("VeryLoooooooooo"                                            //indent:8 exp:8
            + "oongString");                                                    //indent:12 exp:12
        new String(                                                             //indent:8 exp:8
            "correctIndent"                                                     //indent:12 exp:12
        );                                                                      //indent:8 exp:8
    }                                                                           //indent:4 exp:4
}                                                                               //indent:0 exp:0

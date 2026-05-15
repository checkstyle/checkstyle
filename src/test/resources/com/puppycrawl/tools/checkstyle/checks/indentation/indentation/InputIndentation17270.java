// Java17                                                               //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentation17270 {                                        //indent:0 exp:0
    public void method() {                                                 //indent:4 exp:4
        int value = 5;                                                     //indent:8 exp:8

        String result = switch (value) {                                  //indent:8 exp:8
            case 0 -> "zero";                                             //indent:12 exp:12
            case 1, 2 -> "one or two";                                    //indent:12 exp:12
            default -> "other";                                           //indent:12 exp:12
        };                                                                //indent:8 exp:8
    }                                                                     //indent:4 exp:4
}                                                                         //indent:0 exp:0

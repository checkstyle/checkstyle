package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentation17270b {                                   //indent:0 exp:0
    private void test() {                                               //indent:4 exp:4
        int v = 0;                                                      //indent:8 exp:8
        String r = switch (v) {                                         //indent:8 exp:8
            case 0 -> "a";                                              //indent:12 exp:12
            case 1, 2,                                                  //indent:12 exp:12
               3, 4 -> "multiple";                                      //indent:15 exp:16 warn
            default -> "d";                                             //indent:12 exp:12
        };                                                              //indent:8 exp:8
    }                                                                   //indent:4 exp:4
}                                                                       //indent:0 exp:0

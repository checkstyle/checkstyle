package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentation17270linewrapping4 {                                   //indent:0 exp:0
    private void test() {                                                           //indent:4 exp:4
        int v = 0;                                                                  //indent:8 exp:8
        System.out.println(switch (v) {                                             //indent:8 exp:8
            case 0 -> "a";                                                          //indent:12 exp:12
            case 1, 2,                                                              //indent:12 exp:12
              3, 4 -> "under by 2";                                                 //indent:14 exp:16 warn
            case 5, 6,                                                              //indent:12 exp:12
                7, 8 -> "correct";                                                  //indent:16 exp:16
            case 9, 10,                                                             //indent:12 exp:12
               11, 12 -> "under by 1";                                              //indent:15 exp:16 warn
            case 13, 14,                                                            //indent:12 exp:12
               15, 16 -> "under by 1";                                              //indent:15 exp:16 warn
            case 17, 18,                                                            //indent:12 exp:12
                 19, 29 -> "under by 1";                                            //indent:17 exp:>=12
            default -> "d";                                                         //indent:12 exp:12
        });                                                                         //indent:8 exp:8
    }                                                                               //indent:4 exp:4
}                                                                                   //indent:0 exp:0

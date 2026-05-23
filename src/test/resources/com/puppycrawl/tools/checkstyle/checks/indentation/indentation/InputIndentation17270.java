package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentation17270 {                                    //indent:0 exp:0
    private void test() {                                               //indent:4 exp:4
        int v = 0;                                                      //indent:8 exp:8

        String r = switch (v) {                                         //indent:8 exp:8
            case 0 -> "a";                                              //indent:12 exp:12
            case 1 -> "b";                                              //indent:12 exp:12
            default -> "c";                                             //indent:12 exp:12
        };                                                              //indent:8 exp:8

        int n = switch (v) {                                            //indent:8 exp:8
            case 0, 1 -> 1;                                             //indent:12 exp:12
            case 2, 3 -> 2;                                             //indent:12 exp:12
            default -> -1;                                              //indent:12 exp:12
        };                                                              //indent:8 exp:8

        String s = switch (v) {                                         //indent:8 exp:8
            case 0 -> "a";                                              //indent:12 exp:12
            case 1, 2 -> "b";                                           //indent:12 exp:12
            case 3, 4, 5 -> "c";                                        //indent:12 exp:12
            default -> "d";                                             //indent:12 exp:12
        };                                                              //indent:8 exp:8
    }                                                                   //indent:4 exp:4

    private void testWrappedCaseLabels() {                              //indent:4 exp:4
        int v = 0;                                                      //indent:8 exp:8
        System.out.println(switch (v) {                                 //indent:8 exp:8
            case 0 -> "a";                                              //indent:12 exp:12
            case 1, 2,                                                  //indent:12 exp:12
                 3, 4 -> "multiple";                                    //indent:17 exp:20 warn
            default -> "d";                                             //indent:12 exp:12
        });                                                             //indent:8 exp:8
    }                                                                   //indent:4 exp:4
}                                                                       //indent:0 exp:0

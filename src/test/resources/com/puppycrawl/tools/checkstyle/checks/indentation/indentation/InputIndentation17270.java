// Java17                                                               //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentation17270 {                                        //indent:0 exp:0
    private void test() {                                                   //indent:4 exp:4
        int value = 0;                                                      //indent:8 exp:8

        String result = switch (value) {                                    //indent:8 exp:8
            case 0 -> "zero";                                               //indent:12 exp:12
            case 1 -> "one";                                                //indent:12 exp:12
            default -> "other";                                             //indent:12 exp:12
        };                                                                  //indent:8 exp:8

        int number = switch (value) {                                       //indent:8 exp:8
            case 0, 1 -> 1;                                                 //indent:12 exp:12
            case 2, 3 -> 2;                                                 //indent:12 exp:12
            default -> -1;                                                  //indent:12 exp:12
        };                                                                  //indent:8 exp:8

        String output = switch (value) {                                    //indent:8 exp:8
            case 0 -> "zero value";                                         //indent:12 exp:12
            case 1, 2 -> "one or two";                                      //indent:12 exp:12
            case 3, 4, 5 -> "three to five";                                //indent:12 exp:12
            default -> "other value";                                      //indent:12 exp:12
        };                                                                  //indent:8 exp:8
    }                                                                       //indent:4 exp:4
}                                                                           //indent:0 exp:0

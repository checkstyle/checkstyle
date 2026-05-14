// Java21                                                                   //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentation17270 {                                        //indent:0 exp:0
    private void test() {                                                   //indent:4 exp:4
        Object value = null;                                                //indent:8 exp:8

        System.out.println(switch (value) {                                 //indent:8 exp:8
            case null -> "null";                                            //indent:12 exp:12
            case String s, Integer i -> "String or Integer";                //indent:12 exp:12
            default -> "Unknown type";                                      //indent:12 exp:12
        });                                                                 //indent:8 exp:8

        int result = switch (value) {                                       //indent:8 exp:8
            case null -> 0;                                                 //indent:12 exp:12
            case String s, Integer i, Boolean b -> 1;                       //indent:12 exp:12
            default -> 2;                                                   //indent:12 exp:12
        };                                                                  //indent:8 exp:8

        String output = switch (value) {                                    //indent:8 exp:8
            case null, String s -> "null or string";                        //indent:12 exp:12
            case Integer i, Boolean b, Double d -> "number";                //indent:12 exp:12
            case Long l, Short sh, Byte bt -> "other number";               //indent:12 exp:12
            default -> "other";                                             //indent:12 exp:12
        };                                                                  //indent:8 exp:8
    }                                                                       //indent:4 exp:4
}                                                                           //indent:0 exp:0

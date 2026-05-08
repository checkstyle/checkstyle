package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentation17270 {                                         //indent:0 exp:0
    private void test() {                                                   //indent:4 exp:4
        Object value = null;                                                //indent:8 exp:8

        System.out.println(switch (value) {                                 //indent:8 exp:8
            case null -> "null";                                            //indent:12 exp:12
            case String,                                                    //indent:12 exp:12
                 Integer -> "String or Integer";                            //indent:17 exp:17
            default -> "Unknown type";                                      //indent:12 exp:12
        });                                                                 //indent:8 exp:8

        int result = switch (value) {                                       //indent:8 exp:8
            case null -> 0;                                                 //indent:12 exp:12
            case String,                                                    //indent:12 exp:12
                 Integer,                                                   //indent:17 exp:17
                 Boolean -> 1;                                              //indent:17 exp:17
            default -> 2;                                                   //indent:12 exp:12
        };                                                                  //indent:8 exp:8

        String output = switch (value) {                                    //indent:8 exp:8
            case null, String -> "null or string";                          //indent:12 exp:12
            case Integer,                                                   //indent:12 exp:12
                 Boolean,                                                   //indent:17 exp:17
                 Double -> "number";                                        //indent:17 exp:17
            case Long,                                                      //indent:12 exp:12
                 Short,                                                     //indent:17 exp:17
                 Byte -> "other number";                                    //indent:17 exp:17
            default -> "other";                                             //indent:12 exp:12
        };                                                                  //indent:8 exp:8
    }                                                                       //indent:4 exp:4
}                                                                           //indent:0 exp:0

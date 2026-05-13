package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentation17270 {                                        //indent:0 exp:0
    private void test() {                                                   //indent:4 exp:4
        Object value = null;                                                //indent:8 exp:8

        System.out.println(switch (value) {                                 //indent:8 exp:8
            case null -> "null";                                            //indent:12 exp:12
            case String s,                                                  //indent:12 exp:12
                 Integer i -> "String or Integer";                          //indent:17 exp:17
            default -> "Unknown type";                                      //indent:12 exp:12
        });                                                                 //indent:8 exp:8

        int result = switch (value) {                                       //indent:8 exp:8
            case null -> 0;                                                 //indent:12 exp:12
            case String s,                                                  //indent:12 exp:12
                 Integer i,                                                 //indent:17 exp:17
                 Boolean b -> 1;                                            //indent:17 exp:17
            default -> 2;                                                   //indent:12 exp:12
        };                                                                  //indent:8 exp:8

        String output = switch (value) {                                    //indent:8 exp:8
            case null, String s -> "null or string";                        //indent:12 exp:12
            case Integer i,                                                 //indent:12 exp:12
                 Boolean b,                                                 //indent:17 exp:17
                 Double d -> "number";                                      //indent:17 exp:17
            case Long l,                                                    //indent:12 exp:12
                 Short sh,                                                  //indent:17 exp:17
                 Byte bt -> "other number";                                 //indent:17 exp:17
            default -> "other";                                             //indent:12 exp:12
        };                                                                  //indent:8 exp:8
    }                                                                       //indent:4 exp:4
}                                                                           //indent:0 exp:0

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;     //indent:0 exp:0

import java.util.Arrays;                                                    //indent:0 exp:0

public class InputIndentationNestedMethodCalls {                            //indent:0 exp:0
    public static void main(String[] args) {                                //indent:4 exp:4
        Arrays.asList(true,                                                 //indent:8 exp:8
            true,                                                           //indent:12 exp:12
            Arrays.asList(true,                                             //indent:12 exp:12
                true,                                                       //indent:16 exp:16
                true));                                                     //indent:16 exp:16

        Arrays.asList(true,                                                 //indent:8 exp:8
            true,                                                           //indent:12 exp:12
            Arrays.asList(true,                                             //indent:12 exp:12
            true,                                                           //indent:12 exp:16 warn
            true));                                                         //indent:12 exp:16 warn

        Arrays.asList(true,                                                 //indent:8 exp:8
            Arrays.asList(true,                                             //indent:12 exp:12
                Arrays.asList(true,                                         //indent:16 exp:16
                    true,                                                   //indent:20 exp:20
                    true),                                                  //indent:20 exp:20
                true),                                                      //indent:16 exp:16
            true);                                                          //indent:12 exp:12

        Object[] array = new Object[] {                                     //indent:8 exp:8
            1,                                                              //indent:12 exp:12
            new Object[] {                                                  //indent:12 exp:12
                1,                                                          //indent:16 exp:16
                2,                                                          //indent:16 exp:16
                3                                                           //indent:16 exp:16
            },                                                              //indent:12 exp:12
            new Object[] {                                                  //indent:12 exp:12
                1,                                                          //indent:16 exp:16
                2,                                                          //indent:16 exp:16
                new Object[] {                                              //indent:16 exp:16
                    1,                                                      //indent:20 exp:20
                    2                                                       //indent:20 exp:20
                }                                                           //indent:16 exp:16
            }                                                               //indent:12 exp:12
        };                                                                  //indent:8 exp:8
    }                                                                       //indent:4 exp:4
}                                                                           //indent:0 exp:0

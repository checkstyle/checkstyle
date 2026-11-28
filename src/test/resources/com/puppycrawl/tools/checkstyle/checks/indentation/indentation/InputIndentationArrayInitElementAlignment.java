/* Config: */                                                         //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationArrayInitElementAlignment {              //indent:0 exp:0
    int[] numbers = { 1,                                              //indent:4 exp:4
                      2};                                             //indent:22 exp:22
    int[] calls = { identity(1),                                      //indent:4 exp:4
                    2};                                               //indent:20 exp:20
    int[] parentheses = { (1),                                        //indent:4 exp:4
                          2};                                         //indent:26 exp:26
    int[][] nested = { {1},                                           //indent:4 exp:4
                       {2}};                                          //indent:23 exp:23
    int[] commented = { /* note */ 1,                                 //indent:4 exp:4
                                   2};                                //indent:35 exp:35
    String[] strings = { "//",                                        //indent:4 exp:4
                         "/*"};                                       //indent:25 exp:25
    int[] 𝒜 = { 1,                                                    //indent:4 exp:4
                2};                                                   //indent:16 exp:16
    int[] multiline = { /* comment                                    //indent:4 exp:4
        comment */                                                    //indent:8 exp:8
        1                                                             //indent:8 exp:8
    };                                                                //indent:4 exp:4
    int[] empty = {};                                                 //indent:4 exp:4
    int[] trailing = {1,};                                            //indent:4 exp:4
    static int identity(int value) {                                  //indent:4 exp:4
        return value;                                                 //indent:8 exp:8
    }                                                                 //indent:4 exp:4
}                                                                     //indent:0 exp:0

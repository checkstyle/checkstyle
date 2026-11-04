/* Config: */                                                         //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationArrayInitComments {                      //indent:0 exp:0
    void method() {                                                   //indent:4 exp:4
        //below indent:8 exp:8
        int[] plain = {
           1, 2, 3                                                    //indent:11 exp:12 warn
        };                                                            //indent:8 exp:8
        int[] line = { // comment                                     //indent:8 exp:8
           1, 2, 3                                                    //indent:11 exp:12 warn
        };                                                            //indent:8 exp:8
        int[] block = { /* comment */                                 //indent:8 exp:8
           1, 2, 3                                                    //indent:11 exp:12 warn
        };                                                            //indent:8 exp:8
        int[] invalid = { // comment                                  //indent:8 exp:8
                          1                                           //indent:26 exp:12 warn
        };                                                            //indent:8 exp:8
        int[] valid = { // comment                                    //indent:8 exp:8
            1                                                         //indent:12 exp:12
        };                                                            //indent:8 exp:8
    }                                                                 //indent:4 exp:4
}                                                                     //indent:0 exp:0

/* Config:                                                                     //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:    //indent:1 exp:1
 *                                                                             //indent:1 exp:1
 * basicOffset = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                                //indent:1 exp:1
 * lineWrappingIndentation = 4                                                 //indent:1 exp:1
 * tabWidth = 4                                                                //indent:1 exp:1
 */                                                                            //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;        //indent:0 exp:0

public class InputIndentationNewBlockWithoutAssignment {                       //indent:0 exp:0
    void violation() {                                                         //indent:4 exp:4
        new Scanner() {                                                        //indent:8 exp:8
        Object elementStack = new Object();                                    //indent:8 exp:12,16 warn

        @Override                                                              //indent:8 exp:12,16 warn
        void scan(Object value) {                                              //indent:8 exp:8
            if (value == null) {                                               //indent:12 exp:16,20 warn
                return;                                                        //indent:16 exp:20,24 warn
            }                                                                  //indent:12 exp:16,20 warn
        }                                                                      //indent:8 exp:12,16 warn
        }.scan(new Object());                                                  //indent:8 exp:8
    }                                                                          //indent:4 exp:4

    void correct() {                                                           //indent:4 exp:4
        new Scanner() {                                                        //indent:8 exp:8
            Object elementStack = new Object();                                //indent:12 exp:12

            @Override                                                          //indent:12 exp:12
            void scan(Object value) {                                          //indent:12 exp:12
                if (value == null) {                                           //indent:16 exp:16
                    return;                                                    //indent:20 exp:20
                }                                                              //indent:16 exp:16
            }                                                                  //indent:12 exp:12
        }.scan(new Object());                                                  //indent:8 exp:8
    }                                                                          //indent:4 exp:4

    static class Scanner {                                                     //indent:4 exp:4
        void scan(Object value) {                                              //indent:8 exp:8
        }                                                                      //indent:8 exp:8
    }                                                                          //indent:4 exp:4
}                                                                              //indent:0 exp:0

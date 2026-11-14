/* Config:                                                                          //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:         //indent:1 exp:1
 *                                                                                  //indent:1 exp:1
 * basicOffset = 4                                                                  //indent:1 exp:1
 * forceStrictCondition = true                                                      //indent:1 exp:1
 * lineWrappingIndentation = 4                                                      //indent:1 exp:1
 * tabWidth = 4                                                                     //indent:1 exp:1
 *                                                                                  //indent:1 exp:1
 */                                                                                 //indent:1 exp:1
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;             //indent:0 exp:0

public class InputIndentationNestedMethodCallsWithForceStrictCondition {            //indent:0 exp:0
    void nestedCalls() {                                                            //indent:4 exp:4
        firstMethod(                                                                //indent:8 exp:8
            secondMethod(                                                           //indent:12 exp:12
                "string"                                                            //indent:16 exp:16
            )                                                                       //indent:12 exp:12
        );                                                                          //indent:8 exp:8

        firstMethod(                                                                //indent:8 exp:8
            secondMethod(                                                           //indent:12 exp:12
                thirdMethod(                                                        //indent:16 exp:16
                    "string"                                                        //indent:20 exp:20
                )                                                                   //indent:16 exp:16
            )                                                                       //indent:12 exp:12
        );                                                                          //indent:8 exp:8

        firstMethod(                                                                //indent:8 exp:8
            new StringBuilder(                                                      //indent:12 exp:12
                "string"                                                            //indent:16 exp:16
            ).toString()                                                            //indent:12 exp:12
        );                                                                          //indent:8 exp:8

        firstMethod(                                                                //indent:8 exp:8
            secondMethod(                                                           //indent:12 exp:12
            "string"                                                                //indent:12 exp:12
            )                                                                       //indent:12 exp:12
        );                                                                          //indent:8 exp:8

        firstMethod(                                                                //indent:8 exp:8
            secondMethod(                                                           //indent:12 exp:12
                    "string"                                                        //indent:20 exp:12 warn
            )                                                                       //indent:12 exp:12
        );                                                                          //indent:8 exp:8

        firstMethod(                                                                //indent:8 exp:8
            secondMethod(                                                           //indent:12 exp:12
        "string"                                                                    //indent:8 exp:12 warn
            )                                                                       //indent:12 exp:12
        );                                                                          //indent:8 exp:8

        firstMethod(                                                                //indent:8 exp:8
            new StringBuilder(                                                      //indent:12 exp:12
                "string"                                                            //indent:16 exp:16
            )                                                                       //indent:12 exp:12
        );                                                                          //indent:8 exp:8

        firstMethod(                                                                //indent:8 exp:8
            new StringBuilder(                                                      //indent:12 exp:12
                    "string"                                                        //indent:20 exp:12 warn
            )                                                                       //indent:12 exp:12
        );                                                                          //indent:8 exp:8

        firstMethod(                                                                //indent:8 exp:8
            secondMethod(                                                           //indent:12 exp:12
                "string"                                                            //indent:16 exp:16
            ).toString(                                                             //indent:12 exp:12
                )                                                                   //indent:16 exp:8 warn
        );                                                                          //indent:8 exp:8

        firstMethod(                                                                //indent:8 exp:8
            secondMethod(                                                           //indent:12 exp:12
                "x"                                                                 //indent:16 exp:16
            ).toString()                                                            //indent:12 exp:12
        );                                                                          //indent:8 exp:8
    }                                                                               //indent:4 exp:4

    Object nestedCallsInReturn() {                                                  //indent:4 exp:4
        return firstMethod(                                                         //indent:8 exp:8
            secondMethod(                                                           //indent:12 exp:12
                "string"                                                            //indent:16 exp:16
            )                                                                       //indent:12 exp:12
        );                                                                          //indent:8 exp:8
    }                                                                               //indent:4 exp:4

    Object firstMethod(Object object) {                                             //indent:4 exp:4
        return object;                                                              //indent:8 exp:8
    }                                                                               //indent:4 exp:4

    Object secondMethod(Object object) {                                            //indent:4 exp:4
        return object;                                                              //indent:8 exp:8
    }                                                                               //indent:4 exp:4

    Object thirdMethod(Object object) {                                             //indent:4 exp:4
        return object;                                                              //indent:8 exp:8
    }                                                                               //indent:4 exp:4
}                                                                                   //indent:0 exp:0

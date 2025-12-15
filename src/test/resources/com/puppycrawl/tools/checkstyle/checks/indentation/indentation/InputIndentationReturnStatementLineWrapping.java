package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.util.stream.IntStream;                     //indent:0 exp:0

public class InputIndentationReturnStatementLineWrapping { //indent:0 exp:0
    public static int workingIndentationCheck() {          //indent:4 exp:4
        var intermediate = 1                               //indent:8 exp:8
                + 1                                        //indent:16 exp:16
    + 1                                                    //indent:4 exp:16 warn
                        + 1                                //indent:24 exp:16 warn
                    + 1;                                   //indent:20 exp:16 warn
        return intermediate;                               //indent:8 exp:8
    }                                                      //indent:4 exp:4

    public static int failingIndentationCheck() {          //indent:4 exp:4
        return 1                                           //indent:8 exp:8
                + 1                                        //indent:16 exp:16
    + 1                                                    //indent:4 exp:16 warn
                        + 1                                //indent:24 exp:16 warn
                    + 1;                                   //indent:20 exp:16 warn
    }                                                      //indent:4 exp:4

    public static int workingMethodChain() {               //indent:4 exp:4
        var intermediate = IntStream.range(0, 10)          //indent:8 exp:8
                        .filter(i -> i % 2 == 0)           //indent:24 exp:16 warn
            .map(i -> i * 2)                               //indent:12 exp:16 warn
                .sum();                                    //indent:16 exp:16
        return intermediate;                               //indent:8 exp:8
    }                                                      //indent:4 exp:4

    public static int failingMethodChain() {               //indent:4 exp:4
        return IntStream.range(0, 10)                      //indent:8 exp:8
                        .filter(i -> i % 2 == 0)           //indent:24 exp:24
            .map(i -> i * 2)                               //indent:12 exp:12
                .sum();                                    //indent:16 exp:16
    }                                                      //indent:4 exp:4

    public static int incorrectReturnIndent() {            //indent:4 exp:4
      return 1                                             //indent:6 exp:8 warn
                + 1                                        //indent:16 exp:14 warn
                + 1;                                       //indent:16 exp:14 warn
    }                                                      //indent:4 exp:4

    public static int singleLineReturn() {                 //indent:4 exp:4
        return 1 + 1;                                      //indent:8 exp:8
    }                                                      //indent:4 exp:4

    public static int returnWithParentheses() {            //indent:4 exp:4
        return (1                                          //indent:8 exp:8
                + 2                                        //indent:16 exp:16
                + 3);                                      //indent:16 exp:16
    }                                                      //indent:4 exp:4

    public static int returnWithMultiplication() {         //indent:4 exp:4
        return 1                                           //indent:8 exp:8
                * 2                                        //indent:16 exp:16
                * 3;                                       //indent:16 exp:16
    }                                                      //indent:4 exp:4

    public static Object returnNull() {                    //indent:4 exp:4
        return null;                                       //indent:8 exp:8
    }                                                      //indent:4 exp:4

    public static int returnSimpleLiteral() {              //indent:4 exp:4
        return 42;                                         //indent:8 exp:8
    }                                                      //indent:4 exp:4

    public static int returnWithSubtraction() {            //indent:4 exp:4
        return 10                                          //indent:8 exp:8
                - 5                                        //indent:16 exp:16
                - 2;                                       //indent:16 exp:16
    }                                                      //indent:4 exp:4

    public static int returnWithDivision() {               //indent:4 exp:4
        return 100                                         //indent:8 exp:8
                / 10                                       //indent:16 exp:16
                / 2;                                       //indent:16 exp:16
    }                                                      //indent:4 exp:4

    public static int returnWithMixedOperators() {         //indent:4 exp:4
        return 1                                           //indent:8 exp:8
                + 2                                        //indent:16 exp:16
                * 3                                        //indent:16 exp:16
                - 4;                                       //indent:16 exp:16
    }                                                      //indent:4 exp:4

    public static boolean returnWithComparison() {         //indent:4 exp:4
        return 1                                           //indent:8 exp:8
                <                                          //indent:16 exp:16
                2;                                         //indent:16 exp:16
    }                                                      //indent:4 exp:4

    public static boolean returnWithLogicalOr() {          //indent:4 exp:4
        return true                                        //indent:8 exp:8
                ||                                         //indent:16 exp:16
                false;                                     //indent:16 exp:16
    }                                                      //indent:4 exp:4

    public static int returnNestedExpression() {           //indent:4 exp:4
        return (1 + 2)                                     //indent:8 exp:8
                * (3 + 4);                                 //indent:16 exp:16
    }                                                      //indent:4 exp:4

    public static void returnVoid() {                      //indent:4 exp:4
        return;                                            //indent:8 exp:8
    }                                                      //indent:4 exp:4

    public static int returnWithTernary() {                //indent:4 exp:4
        return true                                        //indent:8 exp:8
                ? 1                                        //indent:16 exp:16
                : 2;                                       //indent:16 exp:16
    }                                                      //indent:4 exp:4

    public static String returnWithCast() {                //indent:4 exp:4
        return (String)                                    //indent:8 exp:8
                "test";                                    //indent:16 exp:16
    }                                                      //indent:4 exp:4

    public static int returnWithBitwise() {                //indent:4 exp:4
        return 1                                           //indent:8 exp:8
                &                                          //indent:16 exp:16
                2;                                         //indent:16 exp:16
    }                                                      //indent:4 exp:4

    public static int returnNotOnStartOfLine() { return 1  //indent:4 exp:4
                + 2                                        //indent:16 exp:12 warn
                + 3; }                                     //indent:16 exp:12 warn
}                                                          //indent:0 exp:0


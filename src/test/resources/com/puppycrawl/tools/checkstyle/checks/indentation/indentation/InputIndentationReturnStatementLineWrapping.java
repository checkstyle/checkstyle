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

    public static int returnNotOnStartOfLine() { return 1  //indent:4 exp:4
                + 2                                        //indent:16 exp:12 warn
                + 3; }                                     //indent:16 exp:12 warn

    public static void emptyReturn() {                     //indent:4 exp:4
        return;                                            //indent:8 exp:8
    }                                                      //indent:4 exp:4

    public static int returnWithMethodCall() {             //indent:4 exp:4
        return IntStream.range(0, 10)                      //indent:8 exp:8
                .sum();                                    //indent:16 exp:16
    }                                                      //indent:4 exp:4

    public static int correctReturnIndent() {              //indent:4 exp:4
        return 1                                           //indent:8 exp:8
                + 1;                                       //indent:16 exp:16
    }                                                      //indent:4 exp:4

    public static int returnWithParentheses() {            //indent:4 exp:4
        return (1                                          //indent:8 exp:8
                + 1);                                      //indent:16 exp:16
    }                                                      //indent:4 exp:4

    public static int returnWithTernary() {                //indent:4 exp:4
        return true                                        //indent:8 exp:8
                ? 1 : 2;                                   //indent:16 exp:16
    }                                                      //indent:4 exp:4

    public static int returnWithCast() {                   //indent:4 exp:4
        return (int) (1                                    //indent:8 exp:8
                + 1);                                      //indent:16 exp:16
    }                                                      //indent:4 exp:4

    public static int returnWithMultiLevelIndent() {       //indent:4 exp:4
      return 1                                             //indent:6 exp:8 warn
              + 2                                          //indent:14 exp:14
              + 3;                                         //indent:14 exp:14
    }                                                      //indent:4 exp:4

    public static Object returnWithMultiLineNew() {        //indent:4 exp:4
        return new Object()                                //indent:8 exp:8
                .toString();                               //indent:16 exp:16
    }                                                      //indent:4 exp:4

    public static int returnWithMultiLineMethodCall() {    //indent:4 exp:4
        return IntStream                                   //indent:8 exp:8
                .range(0, 10)                              //indent:16 exp:16
                .sum();                                    //indent:16 exp:16
    }                                                      //indent:4 exp:4

    public static int[] returnWithMultiLineArrayInit() {   //indent:4 exp:4
        return new int[] {                                 //indent:8 exp:8
                1, 2, 3                                    //indent:16 exp:16
        };                                                 //indent:8 exp:8
    }                                                      //indent:4 exp:4

    public static int methodWithBraceOnNewLine()          //indent:4 exp:4
    {                                                      //indent:4 exp:4
      return 1                                             //indent:6 exp:8 warn
              + 2;                                         //indent:14 exp:14
    }                                                      //indent:4 exp:4

    public static int methodWithIfBlock() {               //indent:4 exp:4
        if (true)                                          //indent:8 exp:8
        {                                                  //indent:8 exp:8
      return 1                                             //indent:6 exp:12 warn
              + 2;                                         //indent:14 exp:14
        }                                                  //indent:8 exp:8
    }                                                      //indent:4 exp:4

}                                                          //indent:0 exp:0

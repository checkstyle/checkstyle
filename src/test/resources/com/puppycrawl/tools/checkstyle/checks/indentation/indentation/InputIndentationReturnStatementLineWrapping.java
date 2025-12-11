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
}                                                          //indent:0 exp:0


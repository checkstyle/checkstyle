/* Config:                                                                           //indent:0 exp:0
 * basicOffset = 4                                                                   //indent:1 exp:1
 * braceAdjustment = 0                                                               //indent:1 exp:1
 * caseIndent = 4                                                                    //indent:1 exp:1
 * forceStrictCondition = false                                                      //indent:1 exp:1
 * lineWrappingIndentation = 8                                                       //indent:1 exp:1
 * tabWidth = 4                                                                      //indent:1 exp:1
 * throwsIndent = 8                                                                  //indent:1 exp:1
 * arrayInitIndent = 4                                                               //indent:1 exp:1
 */                                                                                  //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;              //indent:0 exp:0

import java.util.stream.IntStream;                                                   //indent:0 exp:0

public class InputIndentationWrappedReturnNonStrict {                                //indent:0 exp:0

    int arithmetic() {                                                               //indent:4 exp:4
        return 1                                                                     //indent:8 exp:8
                + 1                                                                  //indent:16 exp:16
    + 1                                                                              //indent:4 exp:16 warn
                        + 1                                                          //indent:24 exp:>=16
                    + 1;                                                             //indent:20 exp:>=16
    }                                                                                //indent:4 exp:4

    int chain() {                                                                    //indent:4 exp:4
        return IntStream.range(0, 10)                                                //indent:8 exp:8
                        .filter(i -> i % 2 == 0)                                     //indent:24 exp:>=16
            .map(i -> i * 2)                                                         //indent:12 exp:16 warn
                .sum();                                                              //indent:16 exp:16
    }                                                                                //indent:4 exp:4

    int correct() {                                                                  //indent:4 exp:4
        return 1                                                                     //indent:8 exp:8
                + 1                                                                  //indent:16 exp:16
                + 1;                                                                 //indent:16 exp:16
    }                                                                                //indent:4 exp:4

    void bare() {                                                                    //indent:4 exp:4
        return;                                                                      //indent:8 exp:8
    }                                                                                //indent:4 exp:4

    int arguments() {                                                                //indent:4 exp:4
        return Math.max(                                                             //indent:8 exp:8
            1,                                                                       //indent:12 exp:12
            2);                                                                      //indent:12 exp:12
    }                                                                                //indent:4 exp:4

    int nextLine() {                                                                 //indent:4 exp:4
        return                                                                       //indent:8 exp:8
                1                                                                    //indent:16 exp:16
                + 1;                                                                 //indent:16 exp:16
    }                                                                                //indent:4 exp:4

    int parentheses() {                                                              //indent:4 exp:4
        return (1                                                                    //indent:8 exp:8
                + 1);                                                                //indent:16 exp:16
    }                                                                                //indent:4 exp:4
}                                                                                    //indent:0 exp:0

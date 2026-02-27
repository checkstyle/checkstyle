// Java17                                                                    //indent:0 exp:0

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;      //indent:0 exp:0

import java.util.Optional;                                                   //indent:0 exp:0
import java.util.function.Function;                                          //indent:0 exp:0
import java.util.function.Supplier;                                          //indent:0 exp:0

/* Config:                                                                   //indent:0 exp:0
 * basicOffset = 2                                                           //indent:1 exp:1
 * braceAdjustment = 2                                                       //indent:1 exp:1
 * caseIndent = 2                                                            //indent:1 exp:1
 * tabWidth = 4                                                              //indent:1 exp:1
 * lineWrappingIndentation = 4                                               //indent:1 exp:1
 */                                                                          //indent:1 exp:1

public class InputIndentationLambdaCoverage {                                //indent:0 exp:0

  void testLambdaBlockWrongIndent() {                                        //indent:2 exp:2
    Supplier<Integer> supplier = () -> {                                     //indent:4 exp:4
        return 42;                                                           //indent:8 exp:6 warn
    };                                                                       //indent:4 exp:4
  }                                                                          //indent:2 exp:2

  void testLambdaBlockCorrectIndentMultiLine() {                             //indent:2 exp:2
    Supplier<Integer> supplier = () -> {                                     //indent:4 exp:4
      int x = 10;                                                            //indent:6 exp:6
      return x;                                                              //indent:6 exp:6
    };                                                                       //indent:4 exp:4
  }                                                                          //indent:2 exp:2

  void testLambdaExpressionNoBlock() {                                       //indent:2 exp:2
    Function<Integer, Integer> func = (Integer x) ->                         //indent:4 exp:4
        x * 2;                                                               //indent:8 exp:8
  }                                                                          //indent:2 exp:2

  void testLambdaExpressionNoBlockSameLine() {                               //indent:2 exp:2
    Function<Integer, Integer> func = (Integer x) -> x * 2;                  //indent:4 exp:4
  }                                                                          //indent:2 exp:2

  void testLambdaWrongIndent() {                                             //indent:2 exp:2
    Supplier<Integer> supplier = () ->                                       //indent:4 exp:4
    42;                                                                      //indent:4 exp:8 warn
  }                                                                          //indent:2 exp:2

  void testLambdaBlockSameLineAsArrow() {                                    //indent:2 exp:2
    Supplier<Integer> supplier = () -> { return 42; };                       //indent:4 exp:4
  }                                                                          //indent:2 exp:2

  void testLambdaCorrectIndent() {                                           //indent:2 exp:2
    Supplier<Integer> supplier = () ->                                       //indent:4 exp:4
        42;                                                                  //indent:8 exp:8
  }                                                                          //indent:2 exp:2

  void testLambdaExpressionWrongColumn() {                                   //indent:2 exp:2
    Supplier<Integer> supplier = () ->                                       //indent:4 exp:4
  42;                                                                        //indent:2 exp:8 warn
  }                                                                          //indent:2 exp:2

  void testLambdaAsArgument() {                                              //indent:2 exp:2
    Optional.of("test").map(s ->                                             //indent:4 exp:4
        s.length());                                                         //indent:8 exp:8
  }                                                                          //indent:2 exp:2
}                                                                            //indent:0 exp:0

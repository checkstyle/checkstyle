// Java17                                                                    //indent:0 exp:0

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;      //indent:0 exp:0

/* Config:                                                                   //indent:0 exp:0
 * basicOffset = 2                                                           //indent:1 exp:1
 * braceAdjustment = 2                                                       //indent:1 exp:1
 * caseIndent = 2                                                            //indent:1 exp:1
 * tabWidth = 4                                                              //indent:1 exp:1
 * lineWrappingIndentation = 4                                               //indent:1 exp:1
 */                                                                          //indent:1 exp:1

public class InputIndentationContinuationLines {                             //indent:0 exp:0
  int sum(int a, int b, int c) {                                             //indent:2 exp:2
    return a                                                                 //indent:4 exp:4
    + b                                                                      //indent:4 exp:8 warn
    + c;                                                                     //indent:4 exp:8 warn
  }                                                                          //indent:2 exp:2

  java.util.function.Function<String, Integer> test_arrow() {                //indent:2 exp:2
    return (String s) ->                                                     //indent:4 exp:4
    s.length();                                                              //indent:4 exp:8 warn
  }                                                                          //indent:2 exp:2

  int test_comma() {                                                         //indent:2 exp:2
    return sum(                                                              //indent:4 exp:4
    1,                                                                       //indent:4 exp:8 warn
    2,                                                                       //indent:4 exp:8 warn
    3                                                                        //indent:4 exp:8 warn
    );                                                                       //indent:4 exp:4
  }                                                                          //indent:2 exp:2

  int sum2(int a, int b, int c) {                                            //indent:2 exp:2
    return a + b                                                             //indent:4 exp:4
      + c;                                                                   //indent:6 exp:8 warn
  }                                                                          //indent:2 exp:2

  java.util.function.Function<String, Integer> testArrow() {                 //indent:2 exp:2
    return (String s) ->                                                     //indent:4 exp:4
      s.length();                                                            //indent:6 exp:8 warn
  }                                                                          //indent:2 exp:2

  java.util.function.Function<String, Integer> testArrowSameLine() {         //indent:2 exp:2
    return (String s) -> s.length();                                         //indent:4 exp:4
  }                                                                          //indent:2 exp:2

  int testComma() {                                                          //indent:2 exp:2
    return sum(                                                              //indent:4 exp:4
        1,                                                                   //indent:8 exp:8
        2,                                                                   //indent:8 exp:8
        3                                                                    //indent:8 exp:8
    );                                                                       //indent:4 exp:4
  }                                                                          //indent:2 exp:2

  int testCommaSameLine() {                                                  //indent:2 exp:2
    return sum(1, 2, 3);                                                     //indent:4 exp:4
  }                                                                          //indent:2 exp:2

  void testLambdaWithExpression() {                                          //indent:2 exp:2
    java.util.function.Supplier<Integer> supplier = () ->                    //indent:4 exp:4
        42;                                                                  //indent:8 exp:8
  }                                                                          //indent:2 exp:2

  void testLambdaBlock() {                                                   //indent:2 exp:2
    java.util.function.Supplier<Integer> supplier = () -> {                  //indent:4 exp:4
      return 42;                                                             //indent:6 exp:6
    };                                                                       //indent:4 exp:4
  }                                                                          //indent:2 exp:2
}                                                                            //indent:0 exp:0

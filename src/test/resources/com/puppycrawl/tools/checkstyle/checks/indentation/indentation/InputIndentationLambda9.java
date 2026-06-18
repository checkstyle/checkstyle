// a comment                                                                      //indent:0 exp:0
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;           //indent:0 exp:0

import java.util.function.Function;                                               //indent:0 exp:0

public class InputIndentationLambda9 {                                            //indent:0 exp:0

  Function<String, Integer> method() {                                            //indent:2 exp:2
    return (String s) ->                                                          //indent:4 exp:4
    s.length();                                                                   //indent:4 exp:8 warn
  }                                                                               //indent:2 exp:2

  Function<String, Integer> method1() {                                           //indent:2 exp:2
    Function<String, Integer> function = (String s) ->                            //indent:4 exp:4
        s.length();                                                               //indent:8 exp:8
    return function;                                                              //indent:4 exp:4
  }                                                                               //indent:2 exp:2

  int sum(int a, int b, int c) {                                                  //indent:2 exp:2
    return a                                                                      //indent:4 exp:4
    + b                                                                           //indent:4 exp:8 warn
    + c;                                                                          //indent:4 exp:8 warn
  }                                                                               //indent:2 exp:2

  int test_comma() {                                                              //indent:2 exp:2
    return sum(                                                                   //indent:4 exp:4
    1,                                                                            //indent:4 exp:6 warn
    2,                                                                            //indent:4 exp:6 warn
    3                                                                             //indent:4 exp:6 warn
    );                                                                            //indent:4 exp:4
  }                                                                               //indent:2 exp:2

}                                                                                 //indent:0 exp:0

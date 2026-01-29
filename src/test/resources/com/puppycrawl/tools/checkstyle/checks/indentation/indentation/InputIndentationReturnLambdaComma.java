package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;      //indent:0 exp:0

public class InputIndentationReturnLambdaComma {                            //indent:0 exp:0
  int sum(int a, int b, int c) {                                            //indent:2 exp:2
    return a                                                                 //indent:4 exp:4
    + b                                                                     //indent:4 exp:8 warn
    + c;                                                                    //indent:4 exp:8 warn
  }                                                                         //indent:2 exp:2

  java.util.function.Function<String, Integer> test_arrow() {               //indent:2 exp:2
    return (String s) ->                                                    //indent:4 exp:4
    s.length();                                                             //indent:4 exp:8 warn
  }                                                                         //indent:2 exp:2

  int test_comma() {                                                        //indent:2 exp:2
    return sum(                                                             //indent:4 exp:4
    1,                                                                      //indent:4 exp:8 warn
    2,                                                                      //indent:4 exp:8 warn
    3                                                                       //indent:4 exp:8 warn
    );                                                                      //indent:4 exp:4
  }                                                                         //indent:2 exp:2
}                                                                            //indent:0 exp:0


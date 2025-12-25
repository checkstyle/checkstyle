/* Config:                                                                  //indent:0 exp:0
 * This test-input is intended to be checked using following configuration: //indent:1 exp:1
 *                                                                          //indent:1 exp:1
 * arrayInitIndent = 2                                                      //indent:1 exp:1
 * basicOffset = 2                                                          //indent:1 exp:1
 * braceAdjustment = 2                                                      //indent:1 exp:1
 * caseIndent = 2                                                           //indent:1 exp:1
 * forceStrictCondition = false                                             //indent:1 exp:1
 * lineWrappingIndentation = 4                                              //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * throwsIndent = 4                                                         //indent:1 exp:1
 *                                                                          //indent:1 exp:1
*/                                                                          //indent:0 exp:0

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;     //indent:0 exp:0

import java.util.List;                                                      //indent:0 exp:0
import java.util.stream.Stream;                                             //indent:0 exp:0

class InputIndentationChainedMethodCalls {                                  //indent:0 exp:0

  public InputIndentationChainedMethodCalls(Object... params) {             //indent:2 exp:2
  }                                                                         //indent:2 exp:2

  public String doNothing(String something, String... uselessParams) {      //indent:2 exp:2
    return something;                                                       //indent:4 exp:4
  }                                                                         //indent:2 exp:2

  public InputIndentationChainedMethodCalls getInstance(                    //indent:2 exp:2
          String... uselessParams) {                                        //indent:10 exp:10
    return this;                                                            //indent:4 exp:4
  }                                                                         //indent:2 exp:2

  public static void main(String[] args) {                                  //indent:2 exp:2
    new InputIndentationChainedMethodCalls().getInstance("string_one")      //indent:4 exp:4
    .doNothing("string_one".trim(),                                         //indent:4 exp:8 warn
               "string_two");                                               //indent:15 exp:>=8

    int length = new InputIndentationChainedMethodCalls("param1",           //indent:4 exp:4
                                "param2").getInstance()                     //indent:32 exp:>=8
    .doNothing("nothing")                                                   //indent:4 exp:>=8 warn
    .length();                                                              //indent:4 exp:>=8 warn

    int length2 =                                                           //indent:4 exp:4
    new InputIndentationChainedMethodCalls("param1","param2")               //indent:4 exp:>=8 warn
        .getInstance()                                                      //indent:8 exp:8
        .doNothing("nothing")                                               //indent:8 exp:8
        .length();                                                          //indent:8 exp:8
  }                                                                         //indent:2 exp:2

  public static void testListOfStreamPattern() {                            //indent:2 exp:2
    List.of("foo").stream()                                                 //indent:4 exp:4
        .findFirst()                                                        //indent:8 exp:8
        .ifPresentOrElse(                                                   //indent:8 exp:8
          System.out::println,                                              //indent:10 exp:10
          () -> {                                                           //indent:10 exp:10
            throw new IllegalStateException();                              //indent:12 exp:12
          }                                                                 //indent:10 exp:10
        );                                                                  //indent:8 exp:8

    List.of("a", "b", "c").stream()                                         //indent:4 exp:4
        .filter(s -> s.length() > 1)                                        //indent:8 exp:8
        .forEach(System.out::println);                                      //indent:8 exp:8

    Stream.of("foo")                                                        //indent:4 exp:4
        .findFirst()                                                        //indent:8 exp:8
        .ifPresentOrElse(                                                   //indent:8 exp:8
          System.out::println,                                              //indent:10 exp:10
          () -> {                                                           //indent:10 exp:10
            throw new IllegalStateException();                              //indent:12 exp:12
          }                                                                 //indent:10 exp:10
        );                                                                  //indent:8 exp:8
  }                                                                         //indent:2 exp:2
}                                                                           //indent:0 exp:0

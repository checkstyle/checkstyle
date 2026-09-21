/* Config:                                                                        //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:       //indent:1 exp:1
 *                                                                                //indent:1 exp:1
 * arrayInitIndent = 4                                                            //indent:1 exp:1
 * basicOffset = 2                                                                //indent:1 exp:1
 * braceAdjustment = 2                                                            //indent:1 exp:1
 * caseIndent = 2                                                                 //indent:1 exp:1
 * forceStrictCondition = false                                                   //indent:1 exp:1
 * lineWrappingIndentation = 4                                                    //indent:1 exp:1
 * tabWidth = 4                                                                   //indent:1 exp:1
 * throwsIndent = 4                                                               //indent:1 exp:1
 *                                                                                //indent:1 exp:1
 */                                                                               //indent:1 exp:1
//a comment                                                                       //indent:0 exp:0

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;           //indent:0 exp:0

import java.util.function.Function;                                               //indent:0 exp:0

public class InputIndentationLambdaAndReturnStatement {                           //indent:0 exp:0

  Function<String, Integer> methodIncorrect() {                                   //indent:2 exp:2
    return (String s) ->                                                          //indent:4 exp:4
    s.length();    // ok until #17663                                             //indent:4 exp:4
  }                                                                               //indent:2 exp:2

  Function<String, Integer> methodCorrect() {                                     //indent:2 exp:2
    return (String s) ->                                                          //indent:4 exp:4
        s.length();                                                               //indent:8 exp:8
  }                                                                               //indent:2 exp:2

  Function<String, Integer> methodIncorrect2() {                                  //indent:2 exp:2
    Function<String, Integer> function = (String s) ->                            //indent:4 exp:4
    s.length();                                                                   //indent:4 exp:8 warn
    return function;                                                              //indent:4 exp:4
  }                                                                               //indent:2 exp:2

  int sumIncorrect(int a, int b, int c) {                                         //indent:2 exp:2
    return a                                                                      //indent:4 exp:4
    + b                                                                           //indent:4 exp:8 warn
    + c;                                                                          //indent:4 exp:8 warn
  }                                                                               //indent:2 exp:2

  int testCommaIncorrect() {                                                      //indent:2 exp:2
    return sumIncorrect(                                                          //indent:4 exp:4
    1,    // ok until #17663                                                      //indent:4 exp:4
    2,    // ok until #17663                                                      //indent:4 exp:4
    3     // ok until #17663                                                      //indent:4 exp:4
    );                                                                            //indent:4 exp:4
  }                                                                               //indent:2 exp:2

  int testCommaCorrect() {                                                        //indent:2 exp:2
    return sumIncorrect(                                                          //indent:4 exp:4
        1,                                                                        //indent:8 exp:8
        2,                                                                        //indent:8 exp:8
        3                                                                         //indent:8 exp:8
    );                                                                            //indent:4 exp:4
  }                                                                               //indent:2 exp:2

}                                                                                 //indent:0 exp:0

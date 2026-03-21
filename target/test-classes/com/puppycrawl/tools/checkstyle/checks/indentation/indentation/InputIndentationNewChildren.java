/* Config:                                                                    //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 2                                                        //indent:1 exp:1
 * basicOffset = 2                                                            //indent:1 exp:1
 * braceAdjustment = 2                                                        //indent:1 exp:1
 * caseIndent = 2                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;       //indent:0 exp:0

import java.io.BufferedReader;                                                //indent:0 exp:0
import java.io.IOException;                                                   //indent:0 exp:0
import java.io.InputStreamReader;                                             //indent:0 exp:0
import java.util.Optional;                                                    //indent:0 exp:0

public class InputIndentationNewChildren {                                    //indent:0 exp:0
  public Object foo() {                                                       //indent:2 exp:2
    return Optional.empty()                                                   //indent:4 exp:4
        .orElseThrow(                                                         //indent:8 exp:8
            () ->                                                             //indent:12 exp:12
new IllegalArgumentException(                                                 //indent:0 exp:14,16 warn
"Something wrong 1, something wrong 2, something wrong 3"));                  //indent:0 exp:18,20 warn
  }                                                                           //indent:2 exp:2

  public Object foo1() {                                                      //indent:2 exp:2
    return Optional.empty()                                                   //indent:4 exp:4
        .orElseThrow(                                                         //indent:8 exp:8
            () ->                                                             //indent:12 exp:12
                new IllegalArgumentException(                                 //indent:16 exp:16
"Something wrong 1, something wrong 2, something wrong 3"));                  //indent:0 exp:18,20 warn
  }                                                                           //indent:2 exp:2

  void foo2() throws IOException {                                            //indent:2 exp:2
    BufferedReader bf =                                                       //indent:4 exp:4
        new BufferedReader(                                                   //indent:8 exp:8
        new InputStreamReader(System.in) {                                    //indent:8 exp:12 warn
          int a = 0;                                                          //indent:10 exp:14,16,18 warn
            });                                                               //indent:12 exp:12
  }                                                                           //indent:2 exp:2

  public Object foo4(int data) {                                              //indent:2 exp:2
    return Optional.empty()                                                   //indent:4 exp:4
        .orElseThrow(                                                         //indent:8 exp:8
            () -> new IllegalArgumentException(                               //indent:12 exp:12
"something wrong 1, something wrong 2, something wrong 3"));                  //indent:0 exp:16 warn
  }                                                                           //indent:2 exp:2

  public void createExpressionIssue(Object invocation, String expression) {   //indent:2 exp:2
    throw new IllegalArgumentException("The expression " + expression         //indent:4 exp:4
    + ", which creates" + invocation + " cannot be removed."                  //indent:4 exp:8 warn
    + " Override method `canRemoveExpression` to customize this behavior.");  //indent:4 exp:8 warn
  }                                                                           //indent:2 exp:2

  public Object foo5(int data) {                                              //indent:2 exp:2
    return Optional.empty()                                                   //indent:4 exp:4
        .orElseThrow(                                                         //indent:8 exp:8
    () -> new IllegalArgumentException(                                       //indent:4 exp:10,12 warn
"something wrong 1, something wrong 2, something wrong 3"));                  //indent:0 exp:8 warn
  }                                                                           //indent:2 exp:2

  public Object foo6(int data) {                                              //indent:2 exp:2
    return Optional.empty()                                                   //indent:4 exp:4
        .orElseThrow(                                                         //indent:8 exp:8
            () -> new IllegalArgumentException(                               //indent:12 exp:12
                "something wrong 1, something wrong 2"));                     //indent:16 exp:16
  }                                                                           //indent:2 exp:2
}                                                                             //indent:0 exp:0

//non-compiled with javac: Compilable with Java17                           //indent:0 exp:0

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;     //indent:0 exp:0

/* Config:                                                                  //indent:0 exp:0
 * This test-input is intended to be checked using following configuration: //indent:1 exp:1
 * basicOffset = 2                                                          //indent:1 exp:1
 * braceAdjustment = 2                                                      //indent:1 exp:1
 * caseIndent = 2                                                           //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * lineWrappingIndentation = 4                                              //indent:1 exp:1
 * throwsIndent = 4                                                         //indent:1 exp:1
 */                                                                         //indent:1 exp:1

public class InputIndentationSwitchOnStartOfLine {                          //indent:0 exp:0
  String testMethod1(int i) {                                               //indent:2 exp:2
    String s =                                                              //indent:4 exp:4
        switch (i) {                                                        //indent:8 exp:8
          case 1 -> "one";                                                  //indent:10 exp:10
          case 2 -> "two";                                                  //indent:10 exp:10
          default -> "zero";                                                //indent:10 exp:10
        };                                                                  //indent:8 exp:8
    return s;                                                               //indent:4 exp:4
  }                                                                         //indent:2 exp:2

  void testMethod2(int month) {                                             //indent:2 exp:2
    int result =                                                            //indent:4 exp:4
        switch (month) {                                                    //indent:8 exp:8
          case 1, 6, 7 -> 3;                                                //indent:10 exp:10
          case 2, 9, 10, 11, 12 -> 1;                                       //indent:10 exp:10
          case 3, 5, 4, 8 -> {                                              //indent:10 exp:10
            yield month * 4;                                                //indent:12 exp:12
          }                                                                 //indent:10 exp:10
          default -> 0;                                                     //indent:10 exp:10
        };                                                                  //indent:8 exp:8
  }                                                                         //indent:2 exp:2

  void testMethod3_invalid(int num) {                                       //indent:2 exp:2
    int odd =                                                               //indent:4 exp:4
      switch (num) {                                                        //indent:6 exp:8 warn
        case 1, 3, 7 -> 1;                                                  //indent:8 exp:10 warn
        case 2, 4, 6 -> 2;                                                  //indent:8 exp:10 warn
      };                                                                    //indent:6 exp:8 warn
  }                                                                         //indent:2 exp:2

  String testMethod1_invalid(int i) {                                       //indent:2 exp:2
    String s =                                                              //indent:4 exp:4
          switch (i) {                                                      //indent:10 exp:8 warn
            case 1 -> "one";                                                //indent:12 exp:10 warn
            case 2 -> "two";                                                //indent:12 exp:10 warn
            default -> "zero";                                              //indent:12 exp:10 warn
        };                                                                  //indent:8 exp:8
    return s;                                                               //indent:4 exp:4
  }                                                                         //indent:2 exp:2
}                                                                           //indent:0 exp:0

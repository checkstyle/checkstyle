
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;     //indent:0 exp:0

import java.util.List;                                                      //indent:0 exp:0
/* Config:                                                                  //indent:0 exp:0
 * This test-input is intended to be checked using following configuration: //indent:1 exp:1
 * basicOffset = 2                                                          //indent:1 exp:1
 * braceAdjustment = 2                                                      //indent:1 exp:1
 * caseIndent = 2                                                           //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * lineWrappingIndentation = 4                                              //indent:1 exp:1
 */                                                                         //indent:1 exp:1

public class InputIndentationSwitchExpressionWrappingIndentation {          //indent:0 exp:0
  String testMethod1(int i) {                                               //indent:2 exp:2
    String name = "";                                                       //indent:4 exp:4
    name =                                                                  //indent:4 exp:4
        switch (i) {                                                        //indent:8 exp:8
          case 1 -> "one";                                                  //indent:10 exp:10
          case 2 -> "two";                                                  //indent:10 exp:10
          default -> "zero";                                                //indent:10 exp:10
        };                                                                  //indent:8 exp:8
    return name;                                                            //indent:4 exp:4
  }                                                                         //indent:2 exp:2

  String testMethod2(int x, int y) {                                        //indent:2 exp:2
    return switch (x) {                                                     //indent:4 exp:4
      case 1 ->                                                             //indent:6 exp:6
          switch (y) {                                                      //indent:10 exp:10
            case 2 -> "test";                                               //indent:12 exp:12
            default -> "inner default";                                     //indent:12 exp:12
          };                                                                //indent:10 exp:10
      default -> "outer default";                                           //indent:6 exp:6
    };                                                                      //indent:4 exp:4
  }                                                                         //indent:2 exp:2

  void testMethod3_invalid(int num) {                                       //indent:2 exp:2
    int odd = 0;                                                            //indent:4 exp:4
    odd =                                                                   //indent:4 exp:4
      switch (num) {                                                        //indent:6 exp:8 warn
        case 1, 3, 7 -> 1;                                                  //indent:8 exp:10 warn
        case 2, 4, 6 -> 2;                                                  //indent:8 exp:10 warn
        default -> 0;                                                       //indent:8 exp:10 warn
      };                                                                    //indent:6 exp:8 warn
  }                                                                         //indent:2 exp:2

  String testMethod4_invalid(int x, int y) {                                //indent:2 exp:2
    return switch (x) {                                                     //indent:4 exp:4
      case 1 ->                                                             //indent:6 exp:6
        switch (y) {                                                        //indent:8 exp:10 warn
          case 2 -> "test";                                                 //indent:10 exp:12 warn
            default -> "inner default";                                     //indent:12 exp:12
          };                                                                //indent:10 exp:10
      default -> "outer default";                                           //indent:6 exp:6
    };                                                                      //indent:4 exp:4
  }                                                                         //indent:2 exp:2

  String testMethod3(List<Integer> operations) {                            //indent:2 exp:2
    return operations.stream()                                              //indent:4 exp:4
        .map(                                                               //indent:8 exp:8
            op ->                                                           //indent:12 exp:12
                switch (op) {                                               //indent:16 exp:16
                  case 1 -> "test";                                         //indent:18 exp:18
                  default -> "TEST";                                        //indent:18 exp:18
                })                                                          //indent:16 exp:16
        .findFirst().orElse("defaultValue");                                //indent:8 exp:8
  }                                                                         //indent:2 exp:2
}                                                                           //indent:0 exp:0


package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;     //indent:0 exp:0

import java.util.List;                                                      //indent:0 exp:0
import java.util.function.Predicate;                                        //indent:0 exp:0
import java.util.function.Function;                                         //indent:0 exp:0
/* Config:                                                                  //indent:0 exp:0
 * This test-input is intended to be checked using following configuration: //indent:1 exp:1
 * basicOffset = 2                                                          //indent:1 exp:1
 * braceAdjustment = 2                                                      //indent:1 exp:1
 * caseIndent = 2                                                           //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * lineWrappingIndentation = 4                                              //indent:1 exp:1
 */                                                                         //indent:1 exp:1

public class InputIndentationLambdaChild {                                  //indent:0 exp:0

  String testMethod1(List<Integer> operations) {                            //indent:2 exp:2
    return operations.stream()                                              //indent:4 exp:4
        .map(                                                               //indent:8 exp:8
                op ->                                                       //indent:16 exp:16
                    switch (op) {                                           //indent:20 exp:20
                      case 1 -> "test";                                     //indent:22 exp:22
                      default -> "TEST";                                    //indent:22 exp:22
                    })                                                      //indent:20 exp:20
        .findFirst().orElse("defaultValue");                                //indent:8 exp:8
  }                                                                         //indent:2 exp:2

  void testMethod3() {                                                      //indent:2 exp:2
    switch (1) { default: System.out.println("TEST"); }                     //indent:4 exp:4
    int j = switch (1) { default: yield 1; };                               //indent:4 exp:4
  }                                                                         //indent:2 exp:2

  void main(String[] args) {                                                //indent:2 exp:2
//below indent:4 exp:4
    group((Function<Integer, Integer>) x -> switch (x) { default: yield x; },
//below indent:10 exp:10
          (Function<Integer, Integer>) x -> switch (x) { default: yield x; });
  }                                                                         //indent:2 exp:2

  List<String> getThrowsTrees(Object input) {                               //indent:2 exp:2
    return getBlockTags(input,                                              //indent:4 exp:4
//below indent:8 exp:8
        kind -> switch (kind) { case "EXCEPTION", "THROWS" -> true; default -> false; },
        String.class);                                                      //indent:8 exp:8
  }                                                                         //indent:2 exp:2

  void group(Function<Integer, Integer> f1, Function<Integer, Integer> f2) {//indent:2 exp:2
    // Dummy method                                                         //indent:4 exp:4
  }                                                                         //indent:2 exp:2
//below indent:2 exp:2
  <T> List<T> getBlockTags(Object input, Predicate<String> filter, Class<T> type) {
    return List.of();                                                       //indent:4 exp:4
  }                                                                         //indent:2 exp:2
}                                                                           //indent:0 exp:0

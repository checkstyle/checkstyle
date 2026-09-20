/*
GoogleRightCurly

*/

package com.puppycrawl.tools.checkstyle.checks.blocks.googlerightcurly;

import java.util.List;
import java.util.function.Function;

public class InputGoogleRightCurlySwitchNewStyle2 {

    enum A { A1, A2, A3 }

    enum B { B1, B2, B3 }

    boolean testMethod1(A a, B b, int r) {
        return switch (a) {
            case A1 ->
                    switch (b) {
                        case B1, B2 -> true;
                        case B3 -> throw new IllegalStateException("Unexpected: " + b);
                    };
            case A2, A3 ->
                    switch (b) {
                        case B1 -> true;
                        case B2, B3 -> throw new RuntimeException("Test: " + b);
                    };
        };
    }

    String testMethod2(List<Integer> operations) {
      return operations.stream()
          .map(
              op ->
                  switch (op) {
                    case 1 -> "test";
                    default -> "TEST";
                  })
          .findFirst()
          .orElse("defaultValue");
    }

    void method4() {
      group(
          (Function<Integer, Integer>)
              x ->
                 switch (x) {
                   default:
                     yield x;
                 },
          (Function<Integer, Integer>)
              x ->
                 switch (x) {
                   default:
                     yield x;
                 });
    }

    void group(Function<Integer, Integer> f1, Function<Integer, Integer> f2) {
    }
}

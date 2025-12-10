// Java21

package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

import java.lang.reflect.Proxy;

/** Some javadoc. */
public class InputFormattedLambdaAndChildOnTheSameLine {

  enum A {
    A1,
    A2,
    A3,
    A4,
    A5
  }

  enum B {
    B1,
    B2,
    B3,
    B4,
    B5
  }

  enum R {
    R1,
    R2;

    boolean isOk() {
      return this == R2;
    }
  }

  boolean testMethod1(A a, B b, R r) {
    return switch (a) {
      case A1 ->
          switch (b) {
            case B1, B2 -> true;
            case B3 -> r != R.R1;
            case B4 -> false;
            case B5 -> throw new IllegalStateException("Unexpected: " + b);
          };
      case A2, A3 ->
          switch (b) {
            case B1 -> r.isOk();
            case B2, B3 -> false;
            case B4 -> true;
            case B5 -> throw new RuntimeException("Test: " + b);
          };
      case A4 -> false;
      case A5 -> throw new IllegalArgumentException("Bad A: " + a);
    };
  }

  void testMethod2() {
    var service =
        (CharSequence)
            Proxy.newProxyInstance(
                InputFormattedLambdaAndChildOnTheSameLine.class.getClassLoader(),
                new Class[] {CharSequence.class},
                (proxy, method, methodArgs) ->
                    switch (method.getName()) {
                      case "hashCode" -> 123456789;
                      case "equals" -> methodArgs[0] == proxy;
                      case "toString" -> "FakeInstanceServiceA";
                      default -> throw new IllegalArgumentException(method.getName());
                    });
  }

  void testMethod3() {
    var service =
        (CharSequence)
            Proxy.newProxyInstance(
                InputFormattedLambdaAndChildOnTheSameLine.class.getClassLoader(),
                new Class[] {CharSequence.class},
                (proxy, method, methodArgs) ->
                    switch (method.getName()) {
                      case "hashCode" -> 123456789;
                      case "equals" -> methodArgs[0] == proxy;
                      case "toString" -> "FakeInstanceServiceA";
                      default -> throw new IllegalArgumentException(method.getName());
                    });
  }
}

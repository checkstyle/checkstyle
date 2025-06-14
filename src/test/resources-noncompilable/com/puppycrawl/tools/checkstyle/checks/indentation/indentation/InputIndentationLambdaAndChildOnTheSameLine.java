//non-compiled with javac: Compilable with Java17                           //indent:0 exp:0

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;     //indent:0 exp:0

import java.lang.reflect.Proxy;                                             //indent:0 exp:0

/* Config:                                                                  //indent:0 exp:0
 * This test-input is intended to be checked using following configuration: //indent:1 exp:1
 * basicOffset = 2                                                          //indent:1 exp:1
 * braceAdjustment = 2                                                      //indent:1 exp:1
 * caseIndent = 2                                                           //indent:1 exp:1
 * tabWidth = 4                                                             //indent:1 exp:1
 * lineWrappingIndentation = 4                                              //indent:1 exp:1
 */                                                                         //indent:1 exp:1

public class InputIndentationLambdaAndChildOnTheSameLine {                  //indent:0 exp:0

  enum A { A1, A2, A3, A4, A5 }                                             //indent:2 exp:2

  enum B { B1, B2, B3, B4, B5 }                                             //indent:2 exp:2

  enum R {                                                                  //indent:2 exp:2
    R1, R2;                                                                 //indent:4 exp:4
    boolean isOk() {                                                        //indent:4 exp:4
      return this == R2;                                                    //indent:6 exp:6
    }                                                                       //indent:4 exp:4
  }                                                                         //indent:2 exp:2

  boolean testMethod1(A a, B b, R r) {                                      //indent:2 exp:2
    return switch (a) {                                                     //indent:4 exp:4
      case A1 -> switch (b) {                                               //indent:6 exp:6
        case B1, B2 -> true;                                                //indent:8 exp:8
        case B3 -> r != R.R1;                                               //indent:8 exp:8
        case B4 -> false;                                                   //indent:8 exp:8
        case B5 -> throw new IllegalStateException("Unexpected: " + b);     //indent:8 exp:8
      };                                                                    //indent:6 exp:6
      case A2, A3 ->                                                        //indent:6 exp:6
          switch (b) {                                                      //indent:10 exp:10
            case B1 -> r.isOk();                                            //indent:12 exp:12
            case B2, B3 -> false;                                           //indent:12 exp:12
            case B4 -> true;                                                //indent:12 exp:12
            case B5 -> throw new RuntimeException("Test: " + b);            //indent:12 exp:12
          };                                                                //indent:10 exp:10
      case A4 -> false;                                                     //indent:6 exp:6
      case A5 -> throw new IllegalArgumentException("Bad A: " + a);         //indent:6 exp:6
    };                                                                      //indent:4 exp:4
  }                                                                         //indent:2 exp:2

  void testMethod2() {                                                      //indent:2 exp:2
    var service = (CharSequence) Proxy.newProxyInstance(                    //indent:4 exp:4
        InputIndentationLambdaAndChildOnTheSameLine.class.getClassLoader(), //indent:8 exp:8
        new Class[]{CharSequence.class},                                    //indent:8 exp:8
        (proxy, method, methodArgs) -> switch (method.getName()) {          //indent:8 exp:8
          case "hashCode" -> 123456789;                                     //indent:10 exp:10
          case "equals" -> methodArgs[0] == proxy;                          //indent:10 exp:10
          case "toString" -> "FakeInstanceServiceA";                        //indent:10 exp:10
          default -> throw new IllegalArgumentException(method.getName());  //indent:10 exp:10
        }                                                                   //indent:8 exp:8
    );                                                                      //indent:4 exp:4
  }                                                                         //indent:2 exp:2

  void testMethod3() {                                                      //indent:2 exp:2
    var service = (CharSequence) Proxy.newProxyInstance(                    //indent:4 exp:4
        InputIndentationLambdaAndChildOnTheSameLine.class.getClassLoader(), //indent:8 exp:8
        new Class[]{CharSequence.class},                                    //indent:8 exp:8
        (proxy, method, methodArgs) ->                                      //indent:8 exp:8
            switch (method.getName()) {                                     //indent:12 exp:12
              case "hashCode" -> 123456789;                                 //indent:14 exp:14
              case "equals" -> methodArgs[0] == proxy;                      //indent:14 exp:14
              case "toString" -> "FakeInstanceServiceA";                    //indent:14 exp:14
              default -> throw new IllegalArgumentException(method.getName());//indent:14 exp:14
            }                                                               //indent:12 exp:12
    );                                                                      //indent:4 exp:4
  }                                                                         //indent:2 exp:2

}                                                                           //indent:0 exp:0

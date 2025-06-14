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

  void testMethod1() {                                                      //indent:2 exp:2
    var service = (CharSequence) Proxy.newProxyInstance(                    //indent:4 exp:4
        SwitchIndentationTest.class.getClassLoader(),                       //indent:8 exp:8
        new Class[]{CharSequence.class},                                    //indent:8 exp:8
        (proxy, method, methodArgs) -> switch (method.getName()) {          //indent:8 exp:8
          case "hashCode" -> 123456789;                                     //indent:10 exp:10
          case "equals" -> methodArgs[0] == proxy;                          //indent:10 exp:10
          case "toString" -> "FakeInstanceServiceA";                        //indent:10 exp:10
          default -> throw new IllegalArgumentException(method.getName()); //indent:10 exp:10
        }                                                                   //indent:8 exp:8
    );                                                                      //indent:4 exp:4
  }                                                                         //indent:2 exp:2

  void testMethod2() {                                                      //indent:2 exp:2
    var service = (CharSequence) Proxy.newProxyInstance(                    //indent:4 exp:4
        SwitchIndentationTest.class.getClassLoader(),                       //indent:8 exp:8
        new Class[]{CharSequence.class},                                    //indent:8 exp:8
        (proxy, method, methodArgs) ->                                      //indent:8 exp:8
            switch (method.getName()) {                                     //indent:12 exp:12
              case "hashCode" -> 123456789;                                 //indent:14 exp:14
              case "equals" -> methodArgs[0] == proxy;                      //indent:14 exp:14
              case "toString" -> "FakeInstanceServiceA";                    //indent:14 exp:14
//below indent:14 exp:14
              default -> throw new IllegalArgumentException(method.getName());
            }                                                               //indent:12 exp:12
    );                                                                      //indent:4 exp:4
  }                                                                         //indent:2 exp:2

}                                                                           //indent:0 exp:0

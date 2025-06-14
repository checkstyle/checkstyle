package com.google.checkstyle.test.chapter4formatting.rule42blockindentation;

import java.lang.reflect.Proxy;

/**some javadoc.*/
public class InputLambdaAndChildOnTheSameLineCorrect {

  void testMethod1() {
    var service = (CharSequence) Proxy.newProxyInstance(
        SwitchIndentationTest.class.getClassLoader(),
        new Class[]{CharSequence.class},
        (proxy, method, methodArgs) -> switch (method.getName()) {
          case "hashCode" -> 123456789;
          case "equals" -> methodArgs[0] == proxy;
          case "toString" -> "FakeInstanceServiceA";
          default -> throw new IllegalArgumentException(method.getName());
        }
    );
  }

  void testMethod2() {
    var service = (CharSequence) Proxy.newProxyInstance(
        SwitchIndentationTest.class.getClassLoader(),
        new Class[]{CharSequence.class},
        (proxy, method, methodArgs) ->
            switch (method.getName()) {
              case "hashCode" -> 123456789;
              case "equals" -> methodArgs[0] == proxy;
              case "toString" -> "FakeInstanceServiceA";
              default -> throw new IllegalArgumentException(method.getName());
            }
    );
  }
}

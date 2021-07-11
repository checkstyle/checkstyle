package com.google.checkstyle.test.chapter4formatting.rule413emptyblocks;

public class InputRightCurlyAloneOrEmptyNoViolations {

  private InputRightCurlyAloneOrEmptyNoViolations() {} //ok

  final class BrrrTests extends Object {} // ok

  void method7(int a) {} //ok

  class TestClass4 {} //ok

  enum Test {} //ok

  interface Interface {} //ok

  @interface ClassPreamble { } //ok, should be violation, as there is space

  void method2(java.util.HashSet<String> set) {
    java.util.Map<String, String> map2 = new java.util.LinkedHashMap<String, String>() {{
        put("Hello", "World");
      }}; //ok

    if (set.isEmpty()) {
      while (true) {} //ok
    }
    if (!set.isEmpty()) {
      do {} while (true); //ok
    } else {
      for (int i = 0; i < 10; i++) {} //ok
    }
  }

  void method8(int a) {
    java.util.Map<String, String> map3 = new java.util.LinkedHashMap<String, String>() {{
        put("Hello", "World");
      }

      };
  }
}

@interface TesterAnnotation {} //ok

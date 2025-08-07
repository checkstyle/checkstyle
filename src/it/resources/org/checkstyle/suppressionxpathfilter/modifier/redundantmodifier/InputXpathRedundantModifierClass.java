package org.checkstyle.suppressionxpathfilter.modifier.redundantmodifier;

public class InputXpathRedundantModifierClass {
    public class Example1 {

  void test() {
    try (final var a = lock()) {  //warn

    } catch (Exception e) {

    }
  }
   AutoCloseable lock() {
    return null;
  }
}
}

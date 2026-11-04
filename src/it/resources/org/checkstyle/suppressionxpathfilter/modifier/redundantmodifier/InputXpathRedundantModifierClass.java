/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE, ANNOTATION_DEF, RECORD_DEF, \
         PATTERN_VARIABLE_DEF, LITERAL_CATCH, LAMBDA


*/

package org.checkstyle.suppressionxpathfilter.modifier.redundantmodifier;

public class InputXpathRedundantModifierClass {
    public class Example1 {

  void test() {
    try (final var a = lock()) {  // violation 'Redundant 'final' modifier.'

    } catch (Exception e) {

    }
  }
   AutoCloseable lock() {
    return null;
  }
}
}

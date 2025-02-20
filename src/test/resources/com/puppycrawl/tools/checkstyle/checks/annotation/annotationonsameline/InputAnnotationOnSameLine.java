/*
AnnotationOnSameLine
tokens = CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, \
         VARIABLE_DEF, PARAMETER_DEF, ANNOTATION_DEF, TYPECAST, LITERAL_THROWS, \
         IMPLEMENTS_CLAUSE, TYPE_ARGUMENT, LITERAL_NEW, DOT, ANNOTATION_FIELD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationonsameline;

import static java.lang.annotation.ElementType.TYPE_USE;

import java.lang.annotation.Target;

public
class InputAnnotationOnSameLine {
  @Ann public   // violation, 'Annotation 'Ann' should be on the same line with its target.'
  @Ann2 class E {}

  @Ann private  // violation, 'Annotation 'Ann' should be on the same line with its target.'
  @Ann2 class A {}

  @Ann public int // violation, 'Annotation 'Ann' should be on the same line with its target.'
          field2;

  @Target({TYPE_USE}) @interface Ann {}
  @Target({TYPE_USE}) @interface Ann2 {}
}

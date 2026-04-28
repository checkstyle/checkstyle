/*
RightCurly
option = ALONE_OR_SINGLELINE
tokens = CLASS_DEF, METHOD_DEF, LITERAL_IF, LITERAL_ELSE, LITERAL_DO, LITERAL_WHILE, \
         LITERAL_FOR, STATIC_INIT, INSTANCE_INIT, ANNOTATION_DEF, ENUM_DEF, INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlyAnnotationAndEnum {
      public @interface TestAnnotation {}

      public @interface TestAnnotation1 { String someValue(); }

      public @interface TestAnnotation2 {
            String someValue(); }  // violation ''}' at column 33 should be alone on a line'

      public @interface TestAnnotation3 {
            String someValue();
      }

      public @interface TestAnnotation4 { String someValue();
      }

      enum TestEnum2 {
            SOME_VALUE; } // violation ''}' at column 25 should be alone on a line'

      public @interface LimitedPrivate {
          String[] value();
      };
}

package com.google.checkstyle.test.chapter4formatting.rule452indentcontinuationlines;

/** Some javadoc. */
public class InputIndentationCorrectAnnotationArrayInit {
  interface MyInterface {
    @AnAnnotation(values = {"Hello"})
    void works();

    @interface AnAnnotation {
      String[] values();
    }
  }
}

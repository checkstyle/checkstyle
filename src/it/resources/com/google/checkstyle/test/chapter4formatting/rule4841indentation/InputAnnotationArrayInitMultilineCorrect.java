package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

/** Some annotation. */
@InputAnnotationArrayInitMultilineCorrect.AnnotList({
  @InputAnnotationArrayInitMultilineCorrect.Annot(
      "hello-Goodbye world in one very long and lengthy string in annoation array"),
  @InputAnnotationArrayInitMultilineCorrect.Annot(
      "suppressUnnecessaryWarningsByCheckstyleIndentationCheck Annotation"),
  @InputAnnotationArrayInitMultilineCorrect.Annot(
      "this property is lineWrappingIndenation of Indentation Check")
})
public class InputAnnotationArrayInitMultilineCorrect {
  int testMethod1(int val) {
    return val + 1;
  }

  @interface Annot {
    String value() default "";

    String[] values() default {"Hello", "Checkstyle"};
  }

  @interface AnnotList {
    InputAnnotationArrayInitMultilineCorrect.Annot[] value();
  }
}

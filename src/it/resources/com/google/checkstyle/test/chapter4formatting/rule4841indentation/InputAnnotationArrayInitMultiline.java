package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

/** some annotation. */
@InputAnnotationArrayInitMultiline.AnnotList({
  @InputAnnotationArrayInitMultiline.Annot(
      "hello-Goodbye world in one very long and lengthy string in annoation array"),
  @InputAnnotationArrayInitMultiline.Annot(
      "suppressUnnecessaryWarningsByCheckstyleIndentationCheck Annotation"),
  @InputAnnotationArrayInitMultiline.Annot(
      "this property is lineWrappingIndenation of Indentation Check")
})
public class InputAnnotationArrayInitMultiline {
  int testMethod1(int val) {
    return val + 1;
  }

  @interface Annot {
    String value() default "";

    String[] values() default {"Hello", "Checkstyle"};
  }

  @interface AnnotList {
    InputAnnotationArrayInitMultiline.Annot[] value();
  }
}

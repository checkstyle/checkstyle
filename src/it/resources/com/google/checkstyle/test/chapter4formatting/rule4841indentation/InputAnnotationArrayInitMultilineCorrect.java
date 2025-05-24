package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

/** some annotation. */
@InputAnnotationArrayInitMultilineCorrect.AnnotList({
    @InputAnnotationArrayInitMultilineCorrect.Annot(
      "hello"
    ),
    @InputAnnotationArrayInitMultilineCorrect.Annot(
      "world"
    ),
  @InputAnnotationArrayInitMultilineCorrect.Annot(
      "lineWrappingIndenation"
  )
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

package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/** Some annotation. */
@InputAnnotationArrayInitMultiline.AnnotList({
  @InputAnnotationArrayInitMultiline.Annot(
      "hello-Goodbye world in one very long and lengthy string in annoation array"),
  @InputAnnotationArrayInitMultiline.Annot(
      "suppressUnnecessaryWarningsByCheckstyleIndentationCheck Annotation"),
  @InputAnnotationArrayInitMultiline.Annot(
      "this property is lineWrappingIndenation of Indentation Check")
})
@ComponentShow(
    basePackages = "io.camunda.operate",
    excludeFilters = {
      @ComponentShow.Filter(
          type = FilterClass.REGEX,
          pattern = "io\\.camunda\\.operate\\.zeebeimport\\..*"),
      @ComponentShow.Filter(
          type = FilterClass.REGEX,
          pattern = "io\\.camunda\\.operate\\.webapp\\..*")
    })
public class InputFormattedAnnotationArrayInitMultiline {

  @interface Annot {
    String value() default "";

    String[] values() default {"Hello", "Checkstyle"};
  }

  @interface AnnotList {
    InputAnnotationArrayInitMultiline.Annot[] value();
  }
}

// violation below 'Top-level class FilterClass has to reside in its own source file.'
enum FilterClass {
  REGEX,
  ANNOTATION,
  ASSIGNABLE_TYPE
}

// violation below 'Top-level class ComponentShow has to reside in its own source file.'
@Retention(RetentionPolicy.RUNTIME)
@interface ComponentShow {
  String[] basePackages() default {};

  Filter[] excludeFilters() default {};

  @interface Filter {
    FilterClass type();

    String pattern();
  }
}

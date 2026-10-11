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
@ComponentScan(
    basePackages = "io.camunda.operate",
    excludeFilters = {
      @ComponentScan.Filter(
          type = FilterType.REGEX,
          pattern = "io\\.camunda\\.operate\\.zeebeimport\\..*"),
      @ComponentScan.Filter(
          type = FilterType.REGEX,
          pattern = "io\\.camunda\\.operate\\.webapp\\..*")
    }
)
public class InputAnnotationArrayInitMultiline {

  @interface Annot {
    String value() default "";

    String[] values() default {"Hello", "Checkstyle"};
  }

  @interface AnnotList {
    InputAnnotationArrayInitMultiline.Annot[] value();
  }
}

// violation below 'Top-level class FilterType has to reside in its own source file.'
enum FilterType {
  REGEX,
  ANNOTATION,
  ASSIGNABLE_TYPE
}

// violation below 'Top-level class ComponentScan has to reside in its own source file.'
@Retention(RetentionPolicy.RUNTIME)
@interface ComponentScan {
  String[] basePackages() default {};

  Filter[] excludeFilters() default {};

  @interface Filter {
    FilterType type();

    String pattern();
  }
}

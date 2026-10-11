package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// violation below 'Top-level class TagNameAndDescription has to reside in its own source file.'
@Retention(RetentionPolicy.RUNTIME)
@interface TagNameAndDescription {
  String name();

  String description() default "";

  OptionalCase externalDocs() default @OptionalCase;
}

// violation below 'Top-level class OptionalCase has to reside in its own source file.'
@Retention(RetentionPolicy.RUNTIME)
@interface OptionalCase {
  String description() default "";

  String url() default "";
}

// violation below 'Top-level class Operation has to reside in its own source file.'
@Retention(RetentionPolicy.RUNTIME)
@interface Operation {
  String summary() default "";
}

// violation below 'Top-level class ApiResponsesOne has to reside in its own source file.'
@Retention(RetentionPolicy.RUNTIME)
@interface ApiResponsesOne {
  ApiResponsesTwo[] value();
}

// violation below 'Top-level class ApiResponsesTwo has to reside in its own source file.'
@Retention(RetentionPolicy.RUNTIME)
@interface ApiResponsesTwo {
  String responseCode();

  String description() default "";

  ContentSchemaOne[] content() default {};
}

// violation below 'Top-level class ContentSchemaOne has to reside in its own source file.'
@Retention(RetentionPolicy.RUNTIME)
@interface ContentSchemaOne {
  String mediaType() default "";

  SchemaStatusShow schema() default @SchemaStatusShow;
}

// violation below 'Top-level class SchemaStatusShow has to reside in its own source file.'
@Retention(RetentionPolicy.RUNTIME)
@interface SchemaStatusShow {
  Class<?> implementation() default Void.class;
}

// violation below 'Top-level class ResponseValueShow has to reside in its own source file.'
@Retention(RetentionPolicy.RUNTIME)
@interface ResponseValueShow {
  DummyHttp value();
}

// violation below 'Top-level class DummyHttp has to reside in its own source file.'
enum DummyHttp {
  OK,
  NOT_FOUND,
  INTERNAL_SERVER_ERROR
}

// violation below 'Top-level class ResponseDot has to reside in its own source file.'
class ResponseDot {}

// violation below 'Top-level class ErrorDot has to reside in its own source file.'
class ErrorDot {}

/** Some javadoc. */
@TagNameAndDescription(
    name = "${something1.something2.something3}",
    description = "Endpoint for blah blah blah activities",
    externalDocs =
        @OptionalCase(
            description = "Consuming something from the queue",
            url = "https://google.com"))
public interface InputFormattedAnnotationArrayInitMultiline2 {

  /** Some javadoc. */
  @Operation(summary = "Retrieves something for verifying something")
  @ResponseValueShow(DummyHttp.OK)
  @ApiResponsesOne(
      value = {
        @ApiResponsesTwo(
            responseCode = "500",
            description = "Internal server error occurred",
            content = {
              @ContentSchemaOne(
                  mediaType = "application/json",
                  schema = @SchemaStatusShow(implementation = ErrorDot.class))
            })
      })
  ResponseExitCode<ResponseDot> getAnswer();
}

// violation below 'Top-level class ResponseExitCode has to reside in its own source file.'
class ResponseExitCode<T> {}

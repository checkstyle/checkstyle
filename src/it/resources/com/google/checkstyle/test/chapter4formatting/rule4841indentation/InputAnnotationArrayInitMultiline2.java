package com.google.checkstyle.test.chapter4formatting.rule4841indentation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// violation below 'Top-level class TagNameAndDescriptionNew has to reside in its own source file.'
@Retention(RetentionPolicy.RUNTIME)
@interface TagNameAndDescriptionNew {
  String name();

  String description() default "";

  ExternalDocumentation externalDocs() default @ExternalDocumentation;
}

// violation below 'Top-level class ExternalDocumentation has to reside in its own source file.'
@Retention(RetentionPolicy.RUNTIME)
@interface ExternalDocumentation {
  String description() default "";

  String url() default "";
}

// violation below 'Top-level class OperationFinal has to reside in its own source file.'
@Retention(RetentionPolicy.RUNTIME)
@interface OperationFinal {
  String summary() default "";
}

// violation below 'Top-level class ApiResponses has to reside in its own source file.'
@Retention(RetentionPolicy.RUNTIME)
@interface ApiResponses {
  ApiResponse[] value();
}

// violation below 'Top-level class ApiResponse has to reside in its own source file.'
@Retention(RetentionPolicy.RUNTIME)
@interface ApiResponse {
  String responseCode();

  String description() default "";

  ContentSchema[] content() default {};
}

// violation below 'Top-level class ContentSchema has to reside in its own source file.'
@Retention(RetentionPolicy.RUNTIME)
@interface ContentSchema {
  String mediaType() default "";

  SchemaStatus schema() default @SchemaStatus;
}

// violation below 'Top-level class SchemaStatus has to reside in its own source file.'
@Retention(RetentionPolicy.RUNTIME)
@interface SchemaStatus {
  Class<?> implementation() default Void.class;
}

// violation below 'Top-level class ResponseStatus has to reside in its own source file.'
@Retention(RetentionPolicy.RUNTIME)
@interface ResponseStatus {
  HttpStatus value();
}

// violation below 'Top-level class HttpStatus has to reside in its own source file.'
enum HttpStatus {
  OK,
  NOT_FOUND,
  INTERNAL_SERVER_ERROR
}

// violation below 'Top-level class CustomResponseDto has to reside in its own source file.'
class CustomResponseDto {}

// violation below 'Top-level class ErrorDto has to reside in its own source file.'
class ErrorDto {}

/** Some javadoc. */
@TagNameAndDescriptionNew(
    name = "${something1.something2.something3}",
    description = "Endpoint for blah blah blah activities",
    externalDocs = @ExternalDocumentation(
        description = "Consuming something from the queue",
        url = "https://google.com"
    )
)
public interface InputAnnotationArrayInitMultiline2 {

  /** Some javadoc. */
  @OperationFinal(summary = "Retrieves something for verifying something")
  @ResponseStatus(HttpStatus.OK)
  @ApiResponses(value = {
    @ApiResponse(
        responseCode = "500",
        description = "Internal server error occurred",
        content = {
          @ContentSchema(
              mediaType = "application/json",
              schema = @SchemaStatus(implementation = ErrorDto.class)
          )
        }
    )
  })
  ResponseEntity<CustomResponseDto> getAnswer();
}

// violation below 'Top-level class ResponseEntity has to reside in its own source file.'
class ResponseEntity<T> {}

package com.google.checkstyle.test.chapter4formatting.rule489textblocks;

/** Some javadoc. */
@SuppressWarnings(
    """
       Text block in annotation
    """)
public record InputFormattedTextBlocksInAnnotation(
    @Doc(
            description =
                """
                Some description text.
                A second line so it is a real multi-line text block.
                """)
        String value) {

  @Doc(
      description =
          """
          Field level annotation
          with a text block description.
          """)
  static String field1 = "value";

  @Doc(
      description =
          """
          Field annotation, text block starting on same line.
          """)
  static String str1 =
      """
      Some text.
      """;

  static String str2 =
      """
      Some text.
      """;

  static String str3 =
      """
      Some text.
         Text 2
      """;

  @Doc(
      description =
          """
          Method level annotation
          with a text block description.
          """)
  void method1() {}

  @Doc(
      description =
          """
          Method annotation, text block starting on same line.
          """)
  void method2() {}

  /** Some javadoc. */
  @interface Doc {
    String description();
  }
}

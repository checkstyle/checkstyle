package com.google.checkstyle.test.chapter4formatting.rule489textblocks;

/** Some javadoc. */
@SuppressWarnings(
        """
           Text block in annotation
        """
)
public record InputTextBlocksInAnnotation(
        @Doc(description =
                """
                Some description text.
                A second line so it is a real multi-line text block.
                """)
        String value) {

  @Doc(description =
            """
            Field level annotation
            with a text block description.
            """)
  static String field1 = "value";

  // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
  // violation below 'Each line of text in the text block must be indented'
  @Doc(description = """
            Field annotation, text block starting on same line.
            """)
  // violation above 'Text-block quotes are not vertically aligned'

  // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
  // violation below 'Each line of text in the text block must be indented'
  static String str1 = """
                  Some text.
                  """;
  // violation above 'Text-block quotes are not vertically aligned'

  static String str2 =
            """
              Some text.
              """;
  // violation above 'Text-block quotes are not vertically aligned'

  // violation 2 lines below 'Each line of text in the text block must be indented'
  static String str3 =
            """
          Some text.
             Text 2
            """;

  @Doc(description =
            """
            Method level annotation
            with a text block description.
            """)
  void method1() {
  }


  // violation 3 lines below 'Opening quotes (""") of text-block must be on the new line'
  // violation 2 lines below 'Each line of text in the text block must be indented'
  // violation 3 lines below 'Text-block quotes are not vertically aligned'
  @Doc(description = """
            Method annotation, text block starting on same line.
            """)
  void method2() {
  }

  /** Some javadoc. */
  @interface Doc {
    String description();
  }
}

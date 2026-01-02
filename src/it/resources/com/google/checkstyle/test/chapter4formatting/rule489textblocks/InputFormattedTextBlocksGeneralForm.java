package com.google.checkstyle.test.chapter4formatting.rule489textblocks;

/** Somejavadoc. */
public class InputFormattedTextBlocksGeneralForm {

  /** Some javadoc. */
  public static String textFun() {

    final String simpleScript =
        """
        s
        """;

    final String simpleScript1 =
        """
        this is simple test;
        """;

    getData(
        """
        Hello,
        This is a multi-line message.
        """);

    // violation below 'Opening quotes (""") of text-block must be on the new line'
    return """
    this is sample text
    """; // violation 'Text-block quotes are not vertically aligned'
    // violation above 'incorrect indentation level 4, expected level should be 8.'
  }

  /** Somejavadoc. */
  public String textFun2() {

    final String simpleScript2 =
        """
        this is sample text
        """;

    getData(
        """
        Hello,
        This is a multi-line message.
        """);

    // violation below 'Opening quotes (""") of text-block must be on the new line'
    return """
    THE MULTI-LINE MESSAGE
    """; // violation 'Text-block quotes are not vertically aligned'
    // violation above 'incorrect indentation level 4, expected level should be 8.'
  }

  /** Somejavadoc. */
  public String textFun3() {

    String s =
        """
          Hello there
        """
            + getName();

    getData(
        """
        hello there1
        """,
        0);

    // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
    return s
        + """
        very good
        """
            .charAt(0) // violation above 'Text-block quotes are not vertically aligned'
        + getName();
  }

  /** Somejavadoc. */
  public String getName() {
    return "name";
  }

  /** Somejavadoc. */
  public static void getData(String data) {}

  /** Somejavadoc. */
  public static void getData(String data, int length) {}
}

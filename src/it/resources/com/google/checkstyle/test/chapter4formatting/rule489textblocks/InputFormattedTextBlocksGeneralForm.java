package com.google.checkstyle.test.chapter4formatting.rule489textblocks;

/** somejavadoc. */
public class InputFormattedTextBlocksGeneralForm {

  /** some javadoc. */
  public static void textFun() {

    // until https://github.com/checkstyle/checkstyle/issues/17223
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
  }

  /** somejavadoc. */
  public void textFun2() {

    final String simpleScript2 =
        """
        this is sample text\
        """;
    // until https://github.com/checkstyle/checkstyle/issues/17223

    getData(
        """
        Hello,
        This is a multi-line message.\
        """);
  }

  /** somejavadoc. */
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

    return s
        + """
        very good
        """
            .charAt(0)
        + getName();
  }

  /** somejavadoc. */
  public String getName() {
    return "name";
  }

  /** somejavadoc. */
  public static void getData(String data) {}

  /** somejavadoc. */
  public static void getData(String data, int length) {}
}

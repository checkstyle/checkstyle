package com.google.checkstyle.test.chapter4formatting.rule489textblocks;

/** somejavadoc. */
public class InputFormattedTextBlocksIndentation {

  /** somejavadoc. */
  public void textIndentation1() {
    String e1 =
        """
        content of the block. e1
        """;

    String e2 =
        """
        content of the block of e3
        """;

    String e3 =
        """
        content of the block. e1
        """;

    getData(
        """
        Indentation of Text-block
        """,
        5);
  }

  /** somejavadoc. */
  public void textFuncIndenation2() {
    String e2 =
"""
content of the block e2
""";

    getData(
        """
            Indentation of Text-block
        """,
        5);
  }

  /** somejavadoc. */
  public static void getData(String data, int length) {}
}

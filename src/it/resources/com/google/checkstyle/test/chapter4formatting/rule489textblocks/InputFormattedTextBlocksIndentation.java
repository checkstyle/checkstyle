package com.google.checkstyle.test.chapter4formatting.rule489textblocks;

/** Somejavadoc. */
public class InputFormattedTextBlocksIndentation {

  /** Somejavadoc. */
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

  /** Somejavadoc. */
  public void textFuncIndenation2() {
    // violation 2 lines below '.* incorrect indentation level 0, expected .* 8.'
    String e2 =
"""
content of the block e2
""";
    // violation above '.* incorrect indentation level 0, expected .* 8.'

    getData(
        """
            Indentation of Text-block
        """,
        5);
  }

  /** Somejavadoc. */
  public static void getData(String data, int length) {}
}

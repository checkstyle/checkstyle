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
        """; // until https://github.com/checkstyle/checkstyle/issues/17223

    // until https://github.com/checkstyle/checkstyle/issues/17223
    String e3 =
        """
        content of the block. e1
        """;

    // until https://github.com/checkstyle/checkstyle/issues/17223
    getData(
        """
        Indentation of Text-block
        """, // until https://github.com/checkstyle/checkstyle/issues/17223
        5);
  }

  /** somejavadoc. */
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

  /** somejavadoc. */
  public static void getData(String data, int length) {}
}

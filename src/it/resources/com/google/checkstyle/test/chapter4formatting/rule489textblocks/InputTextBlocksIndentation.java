package com.google.checkstyle.test.chapter4formatting.rule489textblocks;

/** Somejavadoc. */
public class InputTextBlocksIndentation {

  /** Somejavadoc. */
  public void textIndentation1() {
    String e1 =
        """
    content of the block. e1
        """;

    String e2 =
        """
        content of the block of e3
            """; // 'not vertically aligned with opening quotes', ok until #17223

    // 'wrong indentation of 10, expected 8' below, ok until #17223
    String e3 =
          """
      content of the block. e1
        """;
    // not vertically aligned with opening quotes above, ok until #17223

    // 'wrong indentation of 12, expected 8' 2 lines below, ok until #17223
    getData(
            """
        Indentation of Text-block
            """,
        5
    );
  }

  /** Somejavadoc. */
  public void textFuncIndenation2() {
    // violation 2 lines below '.* incorrect indentation level 0, expected .* 8.'
    String e2 =
"""
content of the block e2
""";
    // violation above '.* incorrect indentation level 0, expected .* 8.'

    // violation 2 lines below '.* incorrect indentation level 4, expected .* 6.'
    getData(
    """
        Indentation of Text-block
    """, // violation  '.* incorrect indentation level 4, expected .* 6.'
        5
    );
  }

  /** Somejavadoc. */
  public static void getData(String data, int length) {}
}

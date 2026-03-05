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

    // Expected indentation is the opening quotes on line 22 is 8,
    // Indentation check is lenient when actual indentation is more
    // than expected indentation if `forceStrictCondition` is turned off.
    String e3 =
        """
        content of the block. e1
        """;

    // Expected indentation is the opening quotes on line 30 is 8,
    // Indentation check is lenient when actual indentation is more
    // than expected indentation if `forceStrictCondition` is turned off.
    getData(
        """
        Indentation of Text-block
        """,
        5);
  }

  /** Somejavadoc. */
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

  /** Somejavadoc. */
  public static void getData(String data, int length) {}
}

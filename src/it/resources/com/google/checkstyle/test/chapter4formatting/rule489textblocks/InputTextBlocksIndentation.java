package com.google.checkstyle.test.chapter4formatting.rule489textblocks;

/** Somejavadoc. */
public class InputTextBlocksIndentation {

  /** Somejavadoc. */
  public void textIndentation1() {
    // violation 3 lines below 'Text block content indentation is less than the opening quotes 
    String e1 =
        """
    content of the block. e1
        """;

    String e2 =
        """
        content of the block of e3
            """; // violation 'Text-block quotes are not vertically aligned'

    // Expected indentation is the opening quotes on line 22 is 8,
    // but Indentation check is lenient when actual indentation is more
    // than expected indentation if `forceStrictCondition` is turned off.
    // violation 3 lines below 'Text block content indentation is less than the opening quotes 
    String e3 =
          """
      content of the block. e1
        """; // violation 'Text-block quotes are not vertically aligned'

    // Expected indentation is the opening quotes on line 30 is 8,
    // but Indentation check is lenient when actual indentation is more
    // than expected indentation if `forceStrictCondition` is turned off.
    // violation 3 lines below 'Text block content indentation is less than the opening quotes 
    getData(
            """
        Indentation of Text-block
            """, // Above line's indentation is less, ok until #18227
        5
    );
  }

  /** Somejavadoc. */
  public void textFuncIndenation2() {
    // violation 2 lines below '.* incorrect indentation level 0, expected .* 8.'
    String e2 = // false-positive, ok until #18228
"""
content of the block e2
"""; // false-positive, ok until #18228
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

package com.google.checkstyle.test.chapter4formatting.rule489textblocks;

/** somejavadoc. */
public class InputTextBlocksGeneralForm {

  /** some javadoc. */
  public static String textFun() {

    // opening quotes should be on new line below, ok until #17223
    final String simpleScript = """
        s
        """;
    // not vertically aligned with opening quotes above, ok until #17223

    final String simpleScript1 =
        """
        this is simple test;
        """;

    // opening quotes should be on new line below, ok until #17223
    getData("""
        Hello,
        This is a multi-line message.
        """);
    // not vertically aligned with opening quotes above, ok until #17223

    // opening quotes should be on new line below, ok until #17223
    return """
        this is sample text
        """;
    // not vertically aligned with opening quotes above, ok until #17223
  }

  /** somejavadoc. */
  public String textFun2() {

    final String simpleScript2 =
        """
        this is sample text""";
    // Two violations above, ok until #17223
    //   'closing quotes should be on new line'
    //   'not vertically aligned with opening quotes'

    getData(
        """
        Hello,
        This is a multi-line message."""
    );
    // Two violations 2 lines above, ok until #17223
    //   'closing quotes should be on new line'
    //   'not vertically aligned with opening quotes'

    return
          """
            THE MULTI-LINE MESSAGE""";
    // Two violations above, ok until #17223
    //   'closing quotes should be on new line'
    //   'not vertically aligned with opening quotes'
  }

  /** somejavadoc. */
  public String textFun3() {

    String s =
        """
          Hello there
        """ + getName();

    getData(
        """
        hello there1
        """, 0);

    return s
        +
        """
        very good
        """.charAt(0) + getName();
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

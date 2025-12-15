package com.google.checkstyle.test.chapter4formatting.rule489textblocks;

/** Somejavadoc. */
public class InputTextBlocksGeneralForm {

  /** Some javadoc. */
  public static String textFun() {

    // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
    // violation 2 lines below 'Text block content indentation is less than the opening quotes'
    final String simpleScript = """
        s
        """; // violation 'Text-block quotes are not vertically aligned'

    final String simpleScript1 =
        """
        this is simple test;
        """;

    // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
    // violation 2 lines below 'Text block content indentation is less than the opening quotes'
    getData("""
        Hello,
        This is a multi-line message.
        """); // violation 'Text-block quotes are not vertically aligned'

    // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
    // violation 2 lines below 'Text block content indentation is less than the opening quotes'
    return """
        this is sample text
        """; // violation 'Text-block quotes are not vertically aligned'
  }

  /** Somejavadoc. */
  public String textFun2() {

    final String simpleScript2 =
        """
        this is sample text""";
    // 2 violations above:
    //   'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
    //   'Text-block quotes are not vertically aligned'

    getData(
        """
        Hello,
        This is a multi-line message."""
    ); // 2 violations above:
    // 'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
    // 'Text-block quotes are not vertically aligned'

    return
          """
            THE MULTI-LINE MESSAGE""";
    // 2 violations above:
    // 'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
    // 'Text-block quotes are not vertically aligned'
  }

  /** Somejavadoc. */
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

  /** Somejavadoc. */
  public String getName() {
    return "name";
  }

  /** Somejavadoc. */
  public static void getData(String data) {}

  /** Somejavadoc. */
  public static void getData(String data, int length) {}

}

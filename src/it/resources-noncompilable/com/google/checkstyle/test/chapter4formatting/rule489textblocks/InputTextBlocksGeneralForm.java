package com.google.checkstyle.test.chapter4formatting.rule489textblocks;

/** somejavadoc. */
public class InputTextBlocksGeneralForm {

  /** some javadoc. */
  public static String textFun(String[] args) {

    // until https://github.com/checkstyle/checkstyle/issues/17223
    final String simpleScript = """ 
        s
        """;

    final String simpleScript1 = 
        """
        this is simple test;
        """;

    getData("""
        Hello,
        This is a multi-line message.
        """);

    // until https://github.com/checkstyle/checkstyle/issues/17223
    return """
        this is sample text 
        """;
  }

  /** somejavadoc. */
  public String textFun2() {

    final String simpleScript2 =
        """
        this is sample text """;
    // until https://github.com/checkstyle/checkstyle/issues/17223

    getData(
        """
        Hello,
        This is a multi-line message."""
    );

    return 
          """
            THE MULTI-LINE MESSAGE""";
    // until https://github.com/checkstyle/checkstyle/issues/17223
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
  public void textFunContent() {
    String e1 =
        """
content of the block. e1
        """;

    String e3 = 
        """
            content of the block of e3 
            """;
    
    getData(
        """
    Indentation of Text-block 
        """,
        5
    );
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

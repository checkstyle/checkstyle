package com.google.checkstyle.test.chapter2filebasic.rule232specialescape;

/** Some javadoc. */
public class InputSpecialEscapeSequencesInTextBlockForUnicodeValues {

  class Inner {
    public void wrongEscapeSequences() {
      final String r1 =
          """
          \u0008
          """;
      final String r2 = // violation below 'Consider using special escape sequence .*'
          """
          \u0009
          """;
      final String r3 = // violation below 'Consider using special escape sequence .*'
          """
          \u000csssdfsd
          """;
      final String r4 = // violation below 'Consider using special escape sequence .*'
          """
          \\u000c
          """;
      final String r5 = // violation below 'Consider using special escape sequence .*'
          """
          \\u000d
          """;
      final String r6 = // violation below 'Consider using special escape sequence .*'
          """
          \\u0022
          """;
      final String r7 = // violation below 'Consider using special escape sequence .*'
          """
          \\u0027
          """;
      final String r8 = // violation below 'Consider using special escape sequence .*'
          """
          \\u005c
          """;
    }
  }

  /** Some javadoc. */
  public void specialCharsWithWarnUnicode() {
    String r1 =
        """
        \\u0008
        """;
    String r2 = // violation below 'Consider using special escape sequence .*'
        """
        \\u0009
        """;
    String r3 = // violation below 'Consider using special escape sequence .*'
        """
        \\u000a
        """;
    String r4 = // violation below 'Consider using special escape sequence .*'
        """
        \\u000c
        """;
    String r5 = // violation below 'Consider using special escape sequence .*'
        """
        \\u000d
        """;
    String r6 = // violation below 'Consider using special escape sequence .*'
        """
        \\u0022
        """;
    String r7 = // violation below 'Consider using special escape sequence .*'
        """
        \\u0027
        """;
    String r8 = // violation below 'Consider using special escape sequence .*'
        """
        \\u005c
        """;
  }

  private static void wrongEscapeSequences() {
    final String r1 =
        """
        \u0008
        """;
    final String r2 =
        """
        \u0009
        """; // violation 2 lines above 'Consider using special escape sequence .*'
    final String r3 =
        """
        \u000csssdfsd
        """; // violation 2 lines above 'Consider using special escape sequence .*'
    final String r4 = // violation below 'Consider using special escape sequence .*'
        """
        \u0022
        """; // violation 2 lines above 'Unicode escape(s) usage should be avoided.
    final String r5 = // violation below 'Consider using special escape sequence .*'
        """
        \u000csssdfsd
        """; // violation 2 lines above 'Unicode escape(s) usage should be avoided.
  }

  private static void specialCharsWithWarn() {
    String r1 =
        """
        \\u0008
        """;
    String r2 = // violation below 'Consider using special escape sequence .*'
        """
        \\u0009
        """;
    String r3 = // violation below 'Consider using special escape sequence .*'
        """
        \\u000a
        """;
    String r4 = // violation below 'Consider using special escape sequence .*'
        """
        \\u000c
        """;
    String r5 = // violation below 'Consider using special escape sequence .*'
        """
        \\u000d
        """;
    String r6 = // violation below 'Consider using special escape sequence .*'
        """
        \\u0022
        """;
    String r7 = // violation below 'Consider using special escape sequence .*'
        """
        \\u0027
        """;
    String r8 = // violation below 'Consider using special escape sequence .*'
        """
        \\u005c
        """;
  }

  Inner anoInner =
      new Inner() {
        public void wrongEscapeSequences1() {
          final String r1 =
              """
              \u0008
              """;
          final String r2 = // violation below 'Consider using special escape sequence .*'
              """
              \u0009
              """;
          final String r3 = // violation below 'Consider using special escape sequence .*'
              """
              \u000csssdfsd
              """;
          final String r4 = // violation below 'Consider using special escape sequence .*'
              """
              \u000csssdfsd
              """;
        }

        public void specialCharsWithWarn() {
          String r1 =
              """
              \\u0008
              """;
          String r2 = // violation below 'Consider using special escape sequence .*'
              """
              \\u0009
              """;
          String r3 = // violation below 'Consider using special escape sequence .*'
              """
              \\u000a
              """;
          String r4 = // violation below 'Consider using special escape sequence .*'
              """
              \\u000c
              """;
          String r5 = // violation below 'Consider using special escape sequence .*'
              """
              \\u000d
              """;
          String r6 = // violation below 'Consider using special escape sequence .*'
              """
              \\u0022
              """;
          String r7 = // violation below 'Consider using special escape sequence .*'
              """
              \\u0027
              """;
          String r8 = // violation below 'Consider using special escape sequence .*'
              """
              \\u005c
              """;
        }
      };
}

package com.google.checkstyle.test.chapter2filebasic.rule232specialescape;

/** Some javadoc. */
public class InputSpecialEscapeSequencesInTextBlockForUnicodeValues {

  class Inner {
    public void wrongEscapeSequences() {
      final String r1 = // violation below 'Consider using special escape sequence'
          """
          \u0008
          """;
      final String r2 = // violation below 'Consider using special escape sequence'
          """
          \u0009
          """;
      final String r3 = // violation below 'Consider using special escape sequence'
          """
          \u000csssdfsd
          """;
      final String r4 = // violation below 'Consider using special escape sequence'
          """
          \\u000c
          """;
      final String r5 = // violation below 'Consider using special escape sequence'
          """
          \\u000d
          """;
      final String r6 = // violation below 'Consider using special escape sequence'
          """
          \\u0022
          """;
      final String r7 = // violation below 'Consider using special escape sequence'
          """
          \\u0027
          """;
      final String r8 = // violation below 'Consider using special escape sequence'
          """
          \\u005c
          """;
      // The following have no escape sequences, should not cause violations
      final String r9 =
          """
          \\u000b
          """;
      final String r10 =
          """
          \\u001c
          """;
      final String r11 =
          """
          \\u001D
          """;
      final String r12 =
          """
          \\u001E
          """;
      final String r13 =
          """
          \\u001F
          """;
      final String r14 =
          """
          \\u1680
          """;
      final String r15 =
          """
          \\u2000
          """;
      final String r16 =
          """
          \\u200A
          """;
      final String r17 =
          """
          \\u2028
          """;
      final String r18 =
          """
          \\u2029
          """;
      final String r19 =
          """
          \\u202F
          """;
      final String r20 =
          """
          \\u205F
          """;
      final String r21 =
          """
          \\u3000
          """;
    }
  }

  /** Some javadoc. */
  public void specialCharsWithWarnUnicode() {
    String r1 = // violation below 'Consider using special escape sequence'
        """
        \\u0008
        """;
    String r2 = // violation below 'Consider using special escape sequence'
        """
        \\u0009
        """;
    String r3 = // violation below 'Consider using special escape sequence'
        """
        \\u000a
        """;
    String r4 = // violation below 'Consider using special escape sequence'
        """
        \\u000c
        """;
    String r5 = // violation below 'Consider using special escape sequence'
        """
        \\u000d
        """;
    String r6 = // violation below 'Consider using special escape sequence'
        """
        \\u0020
        """;
    String r7 = // violation below 'Consider using special escape sequence'
        """
        \\u0022
        """;
    String r8 = // violation below 'Consider using special escape sequence'
        """
        \\u0027
        """;
    String r9 = // violation below 'Consider using special escape sequence'
        """
        \\u005c
        """;
    // The following have no escape sequences, should not cause violations
    String r10 =
        """
        \\u000b
        """;
    String r11 =
        """
        \\u001c
        """;
    String r12 =
        """
        \\u1680
        """;
    String r13 =
        """
        \\u2000
        """;
    String r14 =
        """
        \\u3000
        """;
  }

  private static void wrongEscapeSequences() {
    final String r1 = // violation below 'Consider using special escape sequence'
        """
        \u0008
        """;
    final String r2 =
        """
        \u0009
        """; // violation 2 lines above 'Consider using special escape sequence'
    final String r3 =
        """
        \u000csssdfsd
        """; // violation 2 lines above 'Consider using special escape sequence'
    final String r4 = // violation below 'Consider using special escape sequence'
        """
        \u0022
        """; // violation 2 lines above 'Unicode escape(s) usage should be avoided.
    final String r5 = // violation below 'Consider using special escape sequence'
        """
        \u000csssdfsd
        """; // violation 2 lines above 'Unicode escape(s) usage should be avoided.
    // The following have no escape sequences, should not cause violations
    final String r6 =
        """
        \u000b
        """;
    final String r7 =
        """
        \u001c
        """;
    final String r8 =
        """
        \u1680
        """;
    final String r9 =
        """
        \u2000
        """;
    final String r10 =
        """
        \u3000
        """;
  }

  private static void specialCharsWithWarn() {
    String r1 = // violation below 'Consider using special escape sequence'
        """
        \\u0008
        """;
    String r2 = // violation below 'Consider using special escape sequence'
        """
        \\u0009
        """;
    String r3 = // violation below 'Consider using special escape sequence'
        """
        \\u000a
        """;
    String r4 = // violation below 'Consider using special escape sequence'
        """
        \\u000c
        """;
    String r5 = // violation below 'Consider using special escape sequence'
        """
        \\u000d
        """;
    String r6 = // violation below 'Consider using special escape sequence'
        """
        \\u0020
        """;
    String r7 = // violation below 'Consider using special escape sequence'
        """
        \\u0022
        """;
    String r8 = // violation below 'Consider using special escape sequence'
        """
        \\u0027
        """;
    String r9 = // violation below 'Consider using special escape sequence'
        """
        \\u005c
        """;
  }

  Inner anoInner =
      new Inner() {
        public void wrongEscapeSequences1() {
          final String r1 = // violation below 'Consider using special escape sequence'
              """
              \u0008
              """;
          final String r2 = // violation below 'Consider using special escape sequence'
              """
              \u0009
              """;
          final String r3 = // violation below 'Consider using special escape sequence'
              """
              \u000csssdfsd
              """;
          final String r4 = // violation below 'Consider using special escape sequence'
              """
              \u0020
              """;
        }

        public void specialCharsWithWarn() {
          String r1 = // violation below 'Consider using special escape sequence'
              """
              \\u0008
              """;
          String r2 = // violation below 'Consider using special escape sequence'
              """
              \\u0009
              """;
          String r3 = // violation below 'Consider using special escape sequence'
              """
              \\u000a
              """;
          String r4 = // violation below 'Consider using special escape sequence'
              """
              \\u000c
              """;
          String r5 = // violation below 'Consider using special escape sequence'
              """
              \\u000d
              """;
          String r6 = // violation below 'Consider using special escape sequence'
              """
              \\u0020
              """;
          String r7 = // violation below 'Consider using special escape sequence'
              """
              \\u0022
              """;
          String r8 = // violation below 'Consider using special escape sequence'
              """
              \\u0027
              """;
          String r9 = // violation below 'Consider using special escape sequence'
              """
              \\u005c
              """;
          // The following have no escape sequences, should not cause violations
          String r10 =
              """
              \\u000b
              """;
          String r11 =
              """
              \\u001c
              """;
          String r12 =
              """
              \\u1680
              """;
          String r13 =
              """
              \\u2000
              """;
          String r14 =
              """
              \\u3000
              """;
        }
      };
}

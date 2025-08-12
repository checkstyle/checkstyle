package com.google.checkstyle.test.chapter2filebasic.rule232specialescape;

/** Some javadoc. */
public class InputSpecialEscapeSequencesInTextBlockForUnicodeValues {

  class Inner {
    public void wrongEscapeSequences() {
      final String r1 =
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
      final String r9 = // violation below 'Consider using special escape sequence'
          """
          \\u000b
          """;
      final String r10 = // violation below 'Consider using special escape sequence'
          """
          \\u001c
          """;
      final String r11 = // violation below 'Consider using special escape sequence'
          """
          \\u001D
          """;
      final String r12 = // violation below 'Consider using special escape sequence'
          """
          \\u001E
          """;
      final String r13 = // violation below 'Consider using special escape sequence'
          """
          \\u001F
          """;
      final String r14 = // violation below 'Consider using special escape sequence'
          """
          \\u005c
          """;
      final String r15 = // violation below 'Consider using special escape sequence'
          """
          \\u1680
          """;
      final String r16 = // violation below 'Consider using special escape sequence'
          """
          \\u2000
          """;
      final String r17 = // violation below 'Consider using special escape sequence'
          """
          \\u200A
          """;
      final String r18 = // violation below 'Consider using special escape sequence'
           """
           \\u2028
           """;
      final String r19 = // violation below 'Consider using special escape sequence'
           """
           \\u2029
           """;
      final String r20 = // violation below 'Consider using special escape sequence'
          """
          \\u202F
          """;
      final String r21 = // violation below 'Consider using special escape sequence'
          """
          \\u205F
          """;
      final String r22 = // violation below 'Consider using special escape sequence'
          """
          \\u3000
          """;
    }
  }

  /** Some javadoc. */
  public void specialCharsWithWarnUnicode() {
    String r1 =
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
        \\u0022
        """;
    String r7 = // violation below 'Consider using special escape sequence'
        """
        \\u0027
        """;
    String r8 = // violation below 'Consider using special escape sequence'
        """
        \\u005c
        """;
    String r9 = // violation below 'Consider using special escape sequence'
          """
          \\u000b
          """;
    String r10 = // violation below 'Consider using special escape sequence'
          """
          \\u001c
          """;
    String r11 = // violation below 'Consider using special escape sequence'
          """
          \\u001D
          """;
    String r12 = // violation below 'Consider using special escape sequence'
          """
          \\u001E
          """;
    String r13 = // violation below 'Consider using special escape sequence'
          """
          \\u001F
          """;
    String r14 = // violation below 'Consider using special escape sequence'
          """
          \\u005c
          """;
    String r15 = // violation below 'Consider using special escape sequence'
          """
          \\u1680
          """;
    String r16 = // violation below 'Consider using special escape sequence'
          """
          \\u2000
          """;
    String r17 = // violation below 'Consider using special escape sequence'
          """
          \\u200A
          """;
    String r18 = // violation below 'Consider using special escape sequence'
          """
          \\u2028
          """;
    String r19 = // violation below 'Consider using special escape sequence'
          """
          \\u2029
          """;
    String r20 = // violation below 'Consider using special escape sequence'
          """
          \\u202F
          """;
    String r21 = // violation below 'Consider using special escape sequence'
          """
          \\u205F
          """;
    String r22 = // violation below 'Consider using special escape sequence'
          """
          \\u3000
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
    final String r9 = // violation below 'Consider using special escape sequence'
          """
          \u000b
          """;
    final String r10 = // violation below 'Consider using special escape sequence'
          """
          \u001c
          """;
    final String r11 = // violation below 'Consider using special escape sequence'
          """
          \u001D
          """;
    final String r12 = // violation below 'Consider using special escape sequence'
          """
          \u001E
          """;
    final String r13 = // violation below 'Consider using special escape sequence'
          """
          \u001F
          """;
    // violation 2 lines below 'Unicode escape(s) usage should be avoided'
    final String r14 = // violation below 'Consider using special escape sequence'
        """
        \u005c
        """;
    final String r15 = // violation below 'Consider using special escape sequence'
        """
        \u1680
        """;
    final String r16 = // violation below 'Consider using special escape sequence'
          """
          \u2000
          """;
    final String r17 = // violation below 'Consider using special escape sequence'
          """
          \u200A
          """;
    final String r18 = // violation below 'Consider using special escape sequence'
          """
          \u2028
          """;
    final String r19 = // violation below 'Consider using special escape sequence'
          """
          \u2029
          """;
    final String r20 = // violation below 'Consider using special escape sequence'
          """
          \u202F
          """;
    final String r21 = // violation below 'Consider using special escape sequence'
          """
          \u205F
          """;
    final String r22 = // violation below 'Consider using special escape sequence'
          """
          \u3000
          """;
  }

  private static void specialCharsWithWarn() {
    String r1 =
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
        \\u0022
        """;
    String r7 = // violation below 'Consider using special escape sequence'
        """
        \\u0027
        """;
    String r8 = // violation below 'Consider using special escape sequence'
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
              \u000csssdfsd
              """;
        }

        public void specialCharsWithWarn() {
          String r1 =
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
              \\u0022
              """;
          String r7 = // violation below 'Consider using special escape sequence'
              """
              \\u0027
              """;
          String r8 = // violation below 'Consider using special escape sequence'
              """
              \\u005c
              """;
          String r9 = // violation below 'Consider using special escape sequence'
              """
              \\u000b
              """;
          String r10 = // violation below 'Consider using special escape sequence'
              """
              \\u001c
              """;
          String r11 = // violation below 'Consider using special escape sequence'
              """
              \\u001D
              """;
          String r12 = // violation below 'Consider using special escape sequence'
              """
              \\u001E
              """;
          String r13 = // violation below 'Consider using special escape sequence'
              """
              \\u001F
              """;
          String r14 = // violation below 'Consider using special escape sequence'
              """
              \\u005c
              """;
          String r15 = // violation below 'Consider using special escape sequence'
              """
              \\u1680
              """;
          String r16 = // violation below 'Consider using special escape sequence'
              """
              \\u2000
              """;
          String r17 = // violation below 'Consider using special escape sequence'
              """
              \\u200A
              """;
          String r18 = // violation below 'Consider using special escape sequence'
              """
              \\u2028
              """;
          String r19 = // violation below 'Consider using special escape sequence'
              """
              \\u2029
              """;
          String r20 = // violation below 'Consider using special escape sequence'
              """
              \\u202F
              """;
          String r21 = // violation below 'Consider using special escape sequence'
              """
              \\u205F
              """;
          String r22 = // violation below 'Consider using special escape sequence'
              """
              \\u3000
              """;
        }
      };
}

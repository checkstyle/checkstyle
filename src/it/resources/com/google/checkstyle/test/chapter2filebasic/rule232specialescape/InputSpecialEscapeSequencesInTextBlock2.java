package com.google.checkstyle.test.chapter2filebasic.rule232specialescape;

/** Some javadoc. */
public class InputSpecialEscapeSequencesInTextBlock2 {

  class Inner {
    public String wrongEscapeSequences() {
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
    }

    public void specialCharsWithoutWarn() {
      String r2 =
          """
          \t
          """;
      String r3 =
          """
          \n
          """;
      String r4 =
          """
          \f
          """;
      String r5 =
          """
          \r
          """;
      String r6 =
          """
          \"
          """;
      String r7 =
          """
          \'
          """;
      String r8 =
          """
          \\
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
    }

    public void specialCharsWithWarn2() {
      String r1 = // violation below 'Consider using special escape sequence .*'
          """
          \\010
          """;
      String r2 = // violation below 'Consider using special escape sequence .*'
          """
          \\011
          """;
      String r3 = // violation below 'Consider using special escape sequence .*'
          """
          \\012
          """;
      String r4 = // violation below 'Consider using special escape sequence .*'
          """
          \\014
          """;
      String r5 = // violation below 'Consider using special escape sequence .*'
          """
          \\015
          """;
    }

    Inner anoInner =
        new Inner() {
          public String wrongEscapeSequences() {
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
            return // violation below 'Consider using special escape sequence .*'
            """
            \u000csssdfsd
            """;
          }

          public void specialCharsWithoutWarn() {
            String r1 =
                """
                \b
                """;
            String r2 =
                """
                \t
                """;
            String r3 =
                """
                \n
                """;
            String r4 =
                """
                \f
                """;
            String r5 =
                """
                \r
                """;
            String r6 =
                """
                \"
                """;
            String r7 =
                """
                \'
                """;
            String r8 =
                """
                \\
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
          }

          public void specialCharsWithWarn2() {
            String r1 = // violation below 'Consider using special escape sequence .*'
                """
                \\010
                """;
            String r2 = // violation below 'Consider using special escape sequence .*'
                """
                \\011
                """;
            String r3 = // violation below 'Consider using special escape sequence .*'
                """
                \\012
                """;
            String r4 = // violation below 'Consider using special escape sequence .*'
                """
                \\014
                """;
          }
        };
  }
}

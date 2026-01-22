package com.google.checkstyle.test.chapter2filebasic.rule232specialescape;

/** Test for illegal tokens. */
public class InputSpecialEscapeSequencesInTextBlockForOctalValues {

  class Inner {
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
      String r9 =
          """
          \s
          """;
    }
  }

  /** Some javadoc. */
  public void methodWithLiterals() {
    final String ref =
        """
        <a href=\"
        """;
    final String refCase =
        """
        <A hReF=\"
        """;
  }

  /** Some javadoc. */
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
    String r9 =
        """
        \s
        """;
  }

  private void specialCharsWithWarn2() {
    String r1 = // violation below 'Consider using special escape sequence'
        """
        \\010
        """;
    String r2 = // violation below 'Consider using special escape sequence'
        """
        \\011
        """;
    String r3 = // violation below 'Consider using special escape sequence'
        """
        \\012
        """;
    String r4 = // violation below 'Consider using special escape sequence'
        """
        \\014
        """;
    String r5 = // violation below 'Consider using special escape sequence'
        """
        \\015
        """;
    String r6 = // violation below 'Consider using special escape sequence'
        """
        \\042
        """;
    String r7 = // violation below 'Consider using special escape sequence'
        """
        \\047
        """;
    String r8 = // violation below 'Consider using special escape sequence'
        """
        \\134
        """;
    String r9 = // violation below 'Consider using special escape sequence'
        """
        \\040
        """;
  }

  private static void specialCharsWithWarn3() {
    String r1 = // violation below 'Consider using special escape sequence'
        """
        \\010
        """;
    String r2 = // violation below 'Consider using special escape sequence'
        """
        \\011
        """;
    String r3 = // violation below 'Consider using special escape sequence'
        """
        \\012
        """;
    String r4 = // violation below 'Consider using special escape sequence'
        """
        \\014
        """;
    String r5 = // violation below 'Consider using special escape sequence'
        """
        \\015
        """;
    String r6 = // violation below 'Consider using special escape sequence'
        """
        \\040
        """;
    String r7 = // violation below 'Consider using special escape sequence'
        """
        \\042
        """;
    String r8 = // violation below 'Consider using special escape sequence'
        """
        \\047
        """;
    String r9 = // violation below 'Consider using special escape sequence'
        """
        \\134
        """;
  }

  Inner anoInner =
      new Inner() {
        public void specialCharsWithoutWarn1() {
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
          String r9 =
              """
              \s
              """;
        }

        public void specialCharsWithWarn2() {
          String r1 = // violation below 'Consider using special escape sequence'
              """
              \\010
              """;
          String r2 = // violation below 'Consider using special escape sequence'
              """
              \\011
              """;
          String r3 = // violation below 'Consider using special escape sequence'
              """
              \\012
              """;
          String r4 = // violation below 'Consider using special escape sequence'
              """
              \\014
              """;
          String r5 = // violation below 'Consider using special escape sequence'
              """
              \\015
              """;
          String r6 = // violation below 'Consider using special escape sequence'
              """
              \\040
              """;
          String r7 = // violation below 'Consider using special escape sequence'
              """
              \\042
              """;
          String r8 = // violation below 'Consider using special escape sequence'
              """
              \\047
              """;
          String r9 = // violation below 'Consider using special escape sequence'
              """
              \\134
              """;
        }
      };
}

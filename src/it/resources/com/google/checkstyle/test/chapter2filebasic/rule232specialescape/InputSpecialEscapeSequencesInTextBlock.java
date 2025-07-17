package com.google.checkstyle.test.chapter2filebasic.rule232specialescape;

/** Test for illegal tokens. */
public class InputSpecialEscapeSequencesInTextBlock {

  /** some javadoc. */
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

  private String wrongEscapeSequences() {
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

    return // violation below 'Consider using special escape sequence .*'
    """
    \u000csssdfsd
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
  }

  /** Some javadoc. */
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
  }

  private void specialCharsWithWarn2() {
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
}

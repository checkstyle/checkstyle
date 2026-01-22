package com.google.checkstyle.test.chapter2filebasic.rule232specialescape;

/** Test for illegal tokens. */
public class InputSpecialEscapeSequences {

  /** Some javadoc. */
  public void methodWithLiterals() {
    final String ref = "<a href=\"";
    final String refCase = "<A hReF=\"";
  }

  /** Some javadoc. */
  public String wrongEscapeSequences() {
    final String r1 = "\u0008";
    final String r2 = "\u0009"; // violation 'Consider using special escape sequence .*'
    final String r3 = "\u000csssdfsd"; // violation 'Consider using special escape sequence .*'
    final char r5 = '\012'; // violation 'Consider using special escape sequence .*'
    final char r6 = '\u0022'; // violation 'Consider using special escape sequence .*'
    final char r7 = '\b';
    return "\u000csssdfsd"; // violation 'Consider using special escape sequence .*'
  }

  /** Some javadoc. */
  public void specialCharsWithoutWarn() {
    String r1 = "\b";
    String r2 = "\t";
    String r3 = "\n";
    String r4 = "\f";
    String r5 = "\r";
    String r6 = "\"";
    String r7 = "\'";
    String r8 = "\\";
    String r9 = "\s";
  }

  /** Some javadoc. */
  public void specialCharsWithWarn() {
    String r1 = "\\u0008";
    String r2 = "\\u0009"; // violation 'Consider using special escape sequence .*'
    String r3 = "\\u000a"; // violation 'Consider using special escape sequence .*'
    String r4 = "\\u000c"; // violation 'Consider using special escape sequence .*'
    String r5 = "\\u000d"; // violation 'Consider using special escape sequence .*'
    String r6 = "\\u0022"; // violation 'Consider using special escape sequence .*'
    String r7 = "\\u0027"; // violation 'Consider using special escape sequence .*'
    String r8 = "\\u005c"; // violation 'Consider using special escape sequence .*'
  }

  /** Some javadoc. */
  public void specialCharsWithWarn2() {
    String r1 = "\\010"; // violation 'Consider using special escape sequence .*'
    String r2 = "\\011"; // violation 'Consider using special escape sequence .*'
    String r3 = "\\012"; // violation 'Consider using special escape sequence .*'
    String r4 = "\\014"; // violation 'Consider using special escape sequence .*'
    String r5 = "\\015"; // violation 'Consider using special escape sequence .*'
    String r6 = "\\040"; // violation 'Consider using special escape sequence'
    String r7 = "\\042"; // violation 'Consider using special escape sequence .*'
    String r8 = "\\047"; // violation 'Consider using special escape sequence .*'
    String r9 = "\\134"; // violation 'Consider using special escape sequence .*'
  }

  class Inner {
    public String wrongEscapeSequences() {
      final String r1 = "\u0008";
      final String r2 = "\u0009"; // violation 'Consider using special escape sequence .*'
      final String r3 = "\u000csssdfsd";
      // violation above 'Consider using special escape sequence .*'
      final char r5 = '\012'; // violation 'Consider using special escape sequence .*'
      final char r6 = '\u0022'; // violation 'Consider using special escape sequence .*'
      final char r7 = '\b';
      return "\u000csssdfsd"; // violation 'Consider using special escape sequence .*'
    }

    public void specialCharsWithoutWarn() {
      String r1 = "\b";
      String r2 = "\t";
      String r3 = "\n";
      String r4 = "\f";
      String r5 = "\r";
      String r6 = "\"";
      String r7 = "\'";
      String r8 = "\\";
      String r9 = "\s";
    }

    public void specialCharsWithWarn() {
      String r1 = "\\u0008";
      String r2 = "\\u0009"; // violation 'Consider using special escape sequence .*'
      String r3 = "\\u000a"; // violation 'Consider using special escape sequence .*'
      String r4 = "\\u000c"; // violation 'Consider using special escape sequence .*'
      String r5 = "\\u000d"; // violation 'Consider using special escape sequence .*'
      String r6 = "\\u0022"; // violation 'Consider using special escape sequence .*'
      String r7 = "\\u0027"; // violation 'Consider using special escape sequence .*'
      String r8 = "\\u005c"; // violation 'Consider using special escape sequence .*'
    }

    public void specialCharsWithWarn2() {
      String r1 = "\\010"; // violation 'Consider using special escape sequence .*'
      String r2 = "\\011"; // violation 'Consider using special escape sequence .*'
      String r3 = "\\012"; // violation 'Consider using special escape sequence .*'
      String r4 = "\\014"; // violation 'Consider using special escape sequence .*'
      String r5 = "\\015"; // violation 'Consider using special escape sequence .*'
      String r6 = "\\040"; // violation 'Consider using special escape sequence'
      String r7 = "\\042"; // violation 'Consider using special escape sequence .*'
      String r8 = "\\047"; // violation 'Consider using special escape sequence .*'
      String r9 = "\\134"; // violation 'Consider using special escape sequence .*'
    }

    Inner anoInner =
        new Inner() {
          public String wrongEscapeSequences() {
            final String r1 = "\u0008";
            final String r2 = "\u0009"; // violation 'Consider using special escape sequence .*'
            final String r3 = "\u000csssdfsd";
            // violation above 'Consider using special escape sequence .*'
            final char r5 = '\012'; // violation 'Consider using special escape sequence .*'
            final char r6 = '\u0022'; // violation 'Consider using special escape sequence .*'
            final char r7 = '\b';
            return "\u000csssdfsd"; // violation 'Consider using special escape sequence .*'
          }

          public void specialCharsWithoutWarn() {
            String r1 = "\b";
            String r2 = "\t";
            String r3 = "\n";
            String r4 = "\f";
            String r5 = "\r";
            String r6 = "\"";
            String r7 = "\'";
            String r8 = "\\";
            String r9 = "\s";
          }

          public void specialCharsWithWarn() {
            String r1 = "\\u0008";
            String r2 = "\\u0009"; // violation 'Consider using special escape sequence .*'
            String r3 = "\\u000a"; // violation 'Consider using special escape sequence .*'
            String r4 = "\\u000c"; // violation 'Consider using special escape sequence .*'
            String r5 = "\\u000d"; // violation 'Consider using special escape sequence .*'
            String r6 = "\\u0022"; // violation 'Consider using special escape sequence .*'
            String r7 = "\\u0027"; // violation 'Consider using special escape sequence .*'
            String r8 = "\\u005c"; // violation 'Consider using special escape sequence .*'
          }

          public void specialCharsWithWarn2() {
            String r1 = "\\010"; // violation 'Consider using special escape sequence .*'
            String r2 = "\\011"; // violation 'Consider using special escape sequence .*'
            String r3 = "\\012"; // violation 'Consider using special escape sequence .*'
            String r4 = "\\014"; // violation 'Consider using special escape sequence .*'
            String r5 = "\\015"; // violation 'Consider using special escape sequence .*'
            String r6 = "\\040"; // violation 'Consider using special escape sequence'
            String r7 = "\\042"; // violation 'Consider using special escape sequence .*'
            String r8 = "\\047"; // violation 'Consider using special escape sequence .*'
            String r9 = "\\134"; // violation 'Consider using special escape sequence .*'
          }
        };
  }
}

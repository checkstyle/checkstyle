package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

/* Config:
 * default
 */
public class InputWhitespaceAfterCountUnicodeCorrectly {
    String a = "  ";
    String b = "💩💩";
    String c = "💩💩";// violation
    String d = "💩💩"; // ok
}

package com.google.checkstyle.test.chapter2filebasic.rule232specialescape;

class InputSpecialEscapeSequencesForEscapedS {

  void unicode() {
    final String r0 = "\s";
    final String r1 = "\u0020"; // violation 'Consider using special escape sequence'
    final String r2 = "\u0008"; // violation 'Consider using special escape sequence'
    // These have no escape sequences, should not cause violations
    final String r3 = "\u000b";
    final String r4 = "\u001c";
    final String r5 = "\u001D";
    final String r6 = "\u001E";
    final String r7 = "\u001F";
    final String r8 = "\u1680";
    final String r9 = "\u2000";
    final String r10 = "\u200A";
    final String r11 = "\u2028";
    final String r12 = "\u2029";
    final String r13 = "\u202F";
    final String r14 = "\u205F";
    final String r15 = "\u3000";
  }

  void octal() {
    final String r1 = "\040"; // violation 'Consider using special escape sequence'
    final String r2 = "\011"; // violation 'Consider using special escape sequence'
    final String r3 = "\012"; // violation 'Consider using special escape sequence'
    final String r4 = "\010"; // violation 'Consider using special escape sequence'
  }
}

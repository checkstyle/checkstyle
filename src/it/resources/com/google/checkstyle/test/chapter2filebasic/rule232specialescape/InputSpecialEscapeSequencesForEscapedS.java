package com.google.checkstyle.test.chapter2filebasic.rule232specialescape;

class InputSpecialEscapeSequencesForEscapedS {

  void unicode() {
    final String r0 = "\s";
    final String r1 = "\u000b"; // false-negative ok until 17551
    final String r2 = "\u000c"; // violation 'Consider using special escape sequence'
    final String r3 = "\u001c"; // false-negative ok until 17551
    final String r4 = "\u001D"; // false-negative ok until 17551
    final String r5 = "\u001E"; // false-negative ok until 17551
    final String r6 = "\u001F"; // false-negative ok until 17551
    final String r7 = "\u1680"; // false-negative ok until 17551
    final String r8 = "\u2000"; // false-negative ok until 17551
    final String r9 = "\u200A"; // false-negative ok until 17551
    final String r10 = "\u2028"; // false-negative ok until 17551
    final String r12 = "\u2029"; // false-negative ok until 17551
    final String r13 = "\u202F"; // false-negative ok until 17551
    final String r14 = "\u205F"; // false-negative ok until 17551
    final String r15 = "\u3000"; // false-negative ok until 17551
  }

  void octal() {
    final String r1 = "\040"; // false-negative ok until 17551
    final String r2 = "\011"; // violation 'Consider using special escape sequence'
    final String r3 = "\012"; // violation 'Consider using special escape sequence'
  }
}

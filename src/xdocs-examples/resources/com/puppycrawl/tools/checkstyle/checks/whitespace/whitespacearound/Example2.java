/*
WhitespaceAround
tokens = ASSIGN, DIV_ASSIGN, PLUS_ASSIGN, MINUS_ASSIGN, STAR_ASSIGN, \
         MOD_ASSIGN, SR_ASSIGN, BSR_ASSIGN, SL_ASSIGN, BXOR_ASSIGN, \
         BOR_ASSIGN, BAND_ASSIGN


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

class Example2 {
  void example() {
    // xdoc section -- start
    int b=10; // 2 violations
    int c = 10;
    b+=10; // 2 violations
    b += 10;
    c*=10; // 2 violations
    c *= 10;
    c-=5; // 2 violations
    c -= 5;
    c/=2; // 2 violations
    c /= 2;
    c%=1; // 2 violations
    c %= 1;
    c>>=1; // 2 violations
    c >>= 1;
    c>>>=1; // 2 violations
    c >>>= 1;
    // xdoc section -- end
  }
}

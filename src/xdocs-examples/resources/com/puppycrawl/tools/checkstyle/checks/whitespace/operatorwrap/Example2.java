/*
OperatorWrap
option = eol
tokens = ASSIGN, DIV_ASSIGN, PLUS_ASSIGN, MINUS_ASSIGN, STAR_ASSIGN, MOD_ASSIGN, \
           SR_ASSIGN, BSR_ASSIGN, SL_ASSIGN, BXOR_ASSIGN, BOR_ASSIGN,BAND_ASSIGN


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

class Example2 {
  void example() {
    // xdoc section -- start
    int b
            = 10; // violation ''=' should be on the previous line'
    int c =
            10;
    b
            += 10; // violation ''\+=' should be on the previous line'
    b +=
            10;
    c
            *= 10; // violation ''*=' should be on the previous line'
    c
            -= 5; // violation ''-=' should be on the previous line'
    c -=
            5;
    c
            /= 2; // violation ''/=' should be on the previous line'
    c
            %= 1; // violation ''%=' should be on the previous line'
    c
            >>= 1; // violation ''>>=' should be on the previous line'
    c
        >>>= 1; // violation ''>>>=' should be on the previous line'
    c
            &=1 ; // violation ''&=' should be on the previous line'
    c
            <<= 1; // violation ''<<=' should be on the previous line'
    // xdoc section -- end
  }
}

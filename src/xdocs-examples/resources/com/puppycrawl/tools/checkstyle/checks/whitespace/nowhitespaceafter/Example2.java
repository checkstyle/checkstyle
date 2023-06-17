/*
NoWhitespaceAfter
allowLineBreaks = false
tokens = DOT


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

class Example2 {
  // xdoc section -- start
  public void lineBreak(String x) {
    Integer.
        parseInt(x); // violation above ''.' is followed by whitespace'
    Integer.parseInt(x);
  }

  public void dotOperator(String s) {
    Integer.parseInt(s);
    Integer. parseInt(s); // violation ''.' is followed by whitespace'
  }

  public void arrayDec() {
    int[] arr;
    int [] array;
  }

  public void bitwiseNot(int a) {
    a = ~ a;
    a = ~a;
  }
  // xdoc section -- end
}

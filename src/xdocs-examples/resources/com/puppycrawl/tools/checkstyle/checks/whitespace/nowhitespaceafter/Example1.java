/*
NoWhitespaceAfter


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

class Example1 {
  public void lineBreak(String x) {
    Integer.
        parseInt(x);
    Integer.parseInt(x);
  }

  public void dotOperator(String s) {
    Integer.parseInt(s);
    Integer. parseInt(s); // violation ''.' is followed by whitespace'
  }

  public void arrayDec() {
    int[] arr;
    int [] array; // violation ''int' is followed by whitespace'
  }

  public void bitwiseNot(int a) {
    a = ~ a; // violation ''~' is followed by whitespace'
    a = ~a;
  }
}

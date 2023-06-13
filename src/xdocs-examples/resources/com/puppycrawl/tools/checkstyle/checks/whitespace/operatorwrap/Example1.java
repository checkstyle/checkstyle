/*
OperatorWrap


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

class Example1 {
  void example() {
    // xdoc section -- start
    String s = "Hello" + // violation ''\+' should be on a new line'
      "World";

    if (10 == // violation ''==' should be on a new line'
            20) {
    }

    if (10
            ==
            20) { }

    int c = 10 /
            5; // violation above ''/' should be on a new line'

    int d = c
            + 10;
    // xdoc section -- end
  }
}

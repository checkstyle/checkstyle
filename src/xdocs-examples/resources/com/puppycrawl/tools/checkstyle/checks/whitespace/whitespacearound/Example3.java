/*
WhitespaceAround
tokens = LCURLY, RCURLY


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

class Example3 {
  // xdoc section -- start
  void myFunction() {} // violation ''}' is not preceded with whitespace'
  void myFunction2() { }
  // xdoc section -- end
}

/*
WhitespaceAround
allowEmptyConstructors = true


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

// xdoc section -- start
class Example5 {
  public Example5() {}
  public void myFunction() {} // 2 violations
  // no space after '{', no space before '}'
}
// xdoc section -- end

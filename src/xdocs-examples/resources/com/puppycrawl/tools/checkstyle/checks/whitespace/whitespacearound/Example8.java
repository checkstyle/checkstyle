/*
WhitespaceAround
allowEmptyLambdas = true


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

class Example8 {
  void example() {
    // xdoc section -- start
    Runnable noop = () -> {};
    int a=4; // 2 violations
    // xdoc section -- end
  }
}

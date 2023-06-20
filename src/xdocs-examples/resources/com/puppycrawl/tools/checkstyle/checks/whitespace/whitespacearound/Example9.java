/*
WhitespaceAround
allowEmptyCatches = true


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

class Example9 {
  void example() {
    // xdoc section -- start
    int a=4; // 2 violations
    try {
      // body
    } catch (Exception e){}
    // xdoc section -- end
  }
}

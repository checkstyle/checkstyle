/*
WhitespaceAround
allowEmptyCatches = true


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

// xdoc section -- start
class Example9 {
  void example() {
    int a=4; // 2 violations
    try {
    } catch (Exception e){}
  }
}
// xdoc section -- end

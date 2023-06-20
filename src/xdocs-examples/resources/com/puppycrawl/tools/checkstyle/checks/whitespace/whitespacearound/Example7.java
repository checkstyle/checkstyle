/*
WhitespaceAround
allowEmptyLoops = true


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

class Example7 {
  int y = 0;
  void example() {
    // xdoc section -- start
    for (int i = 100;i > 10; i--){}
    do {} while (y == 1);
    int a=4; // 2 violations
    // xdoc section -- end
  }
}

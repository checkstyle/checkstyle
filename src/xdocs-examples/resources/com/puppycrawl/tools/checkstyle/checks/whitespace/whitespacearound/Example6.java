/*
WhitespaceAround
allowEmptyTypes = true


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

// xdoc section -- start
class Example6 {
  class Test {}
  interface testInterface{}
  class anotherTest {
    int a=4; // 2 violations
    // no space before and after '='
  }
}
// xdoc section -- end

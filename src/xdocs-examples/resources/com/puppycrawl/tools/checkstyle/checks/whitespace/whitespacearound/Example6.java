/*
WhitespaceAround
allowEmptyTypes = true


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

class Example6 {
  // xdoc section -- start
  class Test {}
  interface testInterface{}
  class anotherTest {
      int a=4; // 2 violations
  }
  // xdoc section -- end
}

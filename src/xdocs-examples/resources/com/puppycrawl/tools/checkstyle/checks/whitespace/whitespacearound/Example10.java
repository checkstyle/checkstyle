/*
WhitespaceAround
ignoreEnhancedForColon = false


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

class Example10 {
  void example() {
    // xdoc section -- start
    int a=4; // 2 violations
    char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    for (char item: vowels) { // violation '':' is not preceded with whitespace'
      // body
    }
    // xdoc section -- end
  }
}

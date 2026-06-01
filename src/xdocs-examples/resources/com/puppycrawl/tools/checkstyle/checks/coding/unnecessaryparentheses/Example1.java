/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnnecessaryParentheses"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

// xdoc section -- start
class Example1 {
  int a = 10, b = 12;
  boolean x = true, y = false;

  public int square(int a, int b) {
    // violation below, 'Unnecessary parentheses around assignment right-hand side'
    int square = (a * b);
    // violation below, 'Unnecessary parentheses around identifier 'square''
    return (square);
  }

  int sumOfSquares = 0;
  public void sumOfSquares() {
    // violation below, 'Unnecessary parentheses around literal '0''
    for (int i = (0); i < 10; i++) {
      // violation below, 'Unnecessary parentheses around assignment right-hand side'
      int x = (i + 1);
      sumOfSquares += (square(x,x));  // 2 violations
    }
  }

  void method() {
    int x = 9, y = 8;

    if (x >= 0 ^ (x <= 8 & y <= 11) ^ y >= 8) {
      return;
    }
    if (x >= 0 ^ x <= 8 & y <= 11 ^ y >= 8) {
      return;
    }

    if (x >= 0 || (x <= 8 & y <= 11) && y >= 8) {
      return;
    }
    if (x >= 0 || x <= 8 & y <= 11 && y >= 8) {
      return;
    }
  }
}
// xdoc section -- end

/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnnecessaryParentheses"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

import java.util.List;

// xdoc section -- start
class Example1 {
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
  List<String> myList = List.of("a1", "b1", "c1");
  public void filter() {
    myList.stream()
        // violation below, 'Unnecessary parentheses around lambda value'
        .filter((s) -> s.startsWith("c"))
            .forEach(System.out::println);
  }
  int a = 10, b = 12, c = 15;
  boolean x = true, y = false, z= true;
  public void test() {
    // violation below, 'Unnecessary parentheses around expression'
    if ((a >= 0 && b <= 9)
                // violation below, 'Unnecessary parentheses around expression'
                || (c >= 5 && b <= 5)
                // violation below, 'Unnecessary parentheses around expression'
                || (c >= 3 && a <= 7)) {
      return;
    }
    // violation below, 'Unnecessary parentheses around expression'
    if ((-a) != -27
            && b > 5) {
      return;
    }
    if (x==(a <= 15)) {
      return;
    }
    if (x==(y == z)) {
      return;
    }
  }
}
// xdoc section -- end

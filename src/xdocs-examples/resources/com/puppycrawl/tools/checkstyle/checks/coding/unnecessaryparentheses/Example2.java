/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnnecessaryParentheses">
      <property name="tokens" value="BOR, BAND, BXOR" />
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

import java.util.List;

// xdoc section -- start
class Example2 {

  public int square(int a, int b) {
    int square = (a * b);
    return (square);
  }

  int sumOfSquares = 0;
  public void sumOfSquares() {
    for (int i = (0); i < 10; i++) {
      int x = (i + 1);
      sumOfSquares += (square(x,x));
    }
  }

  List<String> myList = List.of("a1", "b1", "c1");
  public void filter() {
    myList.stream()
        .filter((s) -> s.startsWith("c"))
            .forEach(System.out::println);
  }

  int a = 10, b = 12, c = 15;
  boolean x = true, y = false, z = true;
  public void test() {
    if ((a >= 0 && b <= 9) || (c >= 5 && b <= 5) || (c >= 3 && a <= 7)) {
      return;
    }
    if ((-a) != -27 && b > 5) {
      return;
    }
    if (x == (a <= 15)) {
      return;
    }
    if (x == (y == z)) {
      return;
    }
  }

}
// xdoc section -- end

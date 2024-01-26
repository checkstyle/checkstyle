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

        int square = (a * b); // violation 'Unnecessary parentheses around assignment right.*side'
        return (square);      // violation 'Unnecessary parentheses around identifier 'square''
    }
    int sumOfSquares = 0;
    public void sumOfSquares() {
        for (int i = (0); i < 10; i++) {     // violation 'Unnecessary parentheses around literal '0''
            int x = (i + 1);                  // violation 'parentheses around assignment right.*side'
            sumOfSquares += (square(x,x));  // 2 violations
        }
    }
    List<String> myList = List.of("a1", "b1", "c1");
    public void filter() {
        myList.stream()
            .filter((s) -> s.startsWith("c")) // violation 'Unnecessary parentheses around lambda value'
                .forEach(System.out::println);
    }
    int a = 10, b = 12, c = 15;
    boolean x = true, y = false, z= true;
    public void test() {
        if ((a >= 0 && b <= 9)            // violation 'Unnecessary parentheses around expression'
                || (c >= 5 && b <= 5)    // violation 'Unnecessary parentheses around expression'
                || (c >= 3 && a <= 7)) { // violation 'Unnecessary parentheses around expression'
            return;
        }
        if ((-a) != -27 // violation 'Unnecessary parentheses around expression'
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

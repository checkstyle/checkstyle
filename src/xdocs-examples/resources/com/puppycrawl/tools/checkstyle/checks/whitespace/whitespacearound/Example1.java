/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAround"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

// xdoc section -- start
class Example1 {
  public Example1(){} // 3 violations
  // no space after ')' and '{', no space before '}'
  int y = 0;
  int a = 4;

  void example() {
    Runnable noop = () ->{}; // 4 violations
    // no space after '->' and '{', no space before '{' and '}'
    try { }
    catch (Exception e){} // 3 violations
    // no space after ')' and '{', no space before '}'
    char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    for (char item: vowels) { }
    for (int i = 100;i > 10; i--){} // 3 violations
    // no space before '{', no space after '{', no space before '}'
    do {} while (y == 1); // 2 violations
    // no space after '{', no space before '}'
    int i = 0;
    switch (i) {
      case 1: {} // 2 violations
      // no space after '{', no space before '}'
    }
    int a=4; // 2 violations
    // no space before '=', no space after '='
  }

  void myFunction() {} // 2 violations
  // no space after '{', no space before '}'
  void myFunction2() { }
}
// xdoc section -- end

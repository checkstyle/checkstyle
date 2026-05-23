/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAround">
      <property name="allowEmptySwitchBlockStatements" value="true"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

// xdoc section -- start
class Example11 {
  public Example11() { }

  class Test { }
  interface testInterface { }
  class anotherTest {
    int a = 4;
  }

  int y = 0;
  int a = 4;

  void example() {
    int b = 10;
    int c = 10;
    b += 10;
    b += 10;
    c *= 10;
    c *= 10;
    c -= 5;
    c -= 5;
    c /= 2;
    c /= 2;
    c %= 1;
    c %= 1;
    c >>= 1;
    c >>= 1;
    c >>>= 1;
    c >>>= 1;
    Runnable noop = () -> { };
    try { } catch (Exception e) { }
    char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    for (char item : vowels) { }
    for (int i = 100; i > 10; i--) { }
    do { } while (y == 1);
    int i = 0;
    switch (i) {
      case 1: {}
    }
    int a=4; // 2 violations
    // ''=' is not preceded with whitespace.'
    // ''=' is not followed by whitespace.'
  }

  public static void main(String[] args) {
    if (true) { }
    else { }
    for (int i = 1; i > 1; i++) { }
    Runnable noop = () -> { };
    try { }
    catch (Exception e) { }
    char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    for (char item: vowels) {

    }
  }

  public void muFunction() { }
  void myFunction() { }
  void myFunction2() { }
}
// xdoc section -- end

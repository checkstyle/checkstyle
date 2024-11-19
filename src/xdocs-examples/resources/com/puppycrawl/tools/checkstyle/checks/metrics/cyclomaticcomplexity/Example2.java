/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CyclomaticComplexity">
      <property name="max" value="4"/>
      <property name="tokens" value="LITERAL_WHILE, LITERAL_DO"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.metrics.cyclomaticcomplexity;

// xdoc section -- start
class Example2 {
  // Cyclomatic Complexity = 5
  int a, b, c, d, e, n;
  // violation below, 'Cyclomatic Complexity is 5 (max allowed is 4)'
  public void testMethod1() { // 1, function declaration
    while (a < b // 2, while
      && a > c) {
      fun5();
    }
    if (a == b) {
      do { // 3, do
        fun5();
      } while (d==a);
    } else if (c == d) {
      while (c > 0) { // 4, while
        fun5();
      }
      do { // 5, do-while
        fun5();
      } while (a==d);
    }
  }

  public void testMethod2() {
    if (a == b) {
      fun1();
    } else if (a == 0
      && b == c) {
      if (c == -1) {
        fun2();
      }
    } else if (a == c
      || a == d) {
      fun3();
    } else if (d == e) {
      try {
        fun4();
      } catch (Exception e) {
      }
    } else {
      switch(n) {
        case 1:
          fun1();
          break;
        case 2:
          fun2();
          break;
        case 3:
          fun3();
          break;
        default:
          break;
      }
    }
    a = a > 0 ? b : c;
  }

  private void fun5() {}

  private void fun4() {}

  private void fun3() {}

  private void fun2() {}

  private void fun1() {}
}
// xdoc section -- end

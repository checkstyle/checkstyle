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
  int a, b, c, d, e, n;
  // violation below, 'Cyclomatic Complexity is 5 (max allowed is 4)'
  public void testMethod1() { // 1, function declaration
    while (a < b && a > c) { // 2, while
      fun1();
    }
    if (a == b) {
      do { // 3, do
        fun1();
      } while (d==a);
    } else if (c == d) {
      while (c > 0) { // 4, while
        fun1();
      }
      do { // 5, do-while
        fun1();
      } while (a==d);
    }
  }

  public void testMethod2() {
    if (a == b) {
      fun1();
    } else if (a == 0
      && b == c) {
      if (c == -1) {
        fun1();
      }
    } else if (a == c
      || a == d) {
      fun1();
    } else if (d == e) {
      try {
        fun1();
      } catch (Exception e) {
      }
    } else {
      switch(n) {
        case 1:
          fun1();
          break;
        case 2:
          fun1();
          break;
        case 3:
          fun1();
          break;
        default:
          break;
      }
    }
    a = a > 0 ? b : c;
  }
  private void fun1() {}
}
// xdoc section -- end

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
  int a, b, c, d;
  // violation below, 'Cyclomatic Complexity is 5 (max allowed is 4)'
  public void foo() { // 1, function declaration
    while (a < b // 2, while
      && a > c) {
      fun();
    }
    if (a == b) {
      do { // 3, do
        fun();
      } while (d==a);
    } else if (c == d) {
      while (c > 0) { // 4, while
        fun();
      }
      do { // 5, do-while
        fun();
      } while (a==d);
    }
  }

  public void fun() {}
}
// xdoc section -- end

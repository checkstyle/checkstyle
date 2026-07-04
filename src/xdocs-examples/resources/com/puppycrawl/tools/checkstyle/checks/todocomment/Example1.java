/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TodoComment"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.todocomment;

// xdoc section -- start

/**
 =========================================
 Box comment with equals
 =========================================
 */
public class Example1 {
  int i;
  int x;

  // =========

  public void test() {
    // TODO: do differently in future
    // violation above 'matches to-do format'
    i++;

    // todo: do differently in future

    i++;

    // FIXME: handle x = 0 case

    i = i / x;
  }
  // *********

  public void method1() {
    int y = 2;
  }
  // #########

  public void method2() {
    int z = 3;
  }
  // ###### (only 6 chars)
  public void method4() {
    int b = 5;
  }
}
// xdoc section -- end

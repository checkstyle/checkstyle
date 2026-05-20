/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MatchXpath">
      <property name="id" value="largeConstantCollection"/>
      <property name="query"
           value="//ARRAY_INIT[count(./EXPR) > 10]"/>
      <message key="matchxpath.match"
           value="Array initialization should contain at most 10 elements"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

import java.util.HashMap;

// xdoc section -- start
public class Example6 {
  // violation below 'Array initialization should contain at most 10 elements'
  int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

  int[] small = {1, 2, 3};

  public Example6() { }
  public Example6(Object c) { }
  public Example6(int a, HashMap<String, Integer> b) { }

  public void method1() { }
  private void method2() { }
  public void method3() { }
  private void method4() { }
  public void method5() { }
  private void method6() { }

  public void test() {}
  public void getName() {}
  public void foo() {}
  public void sayHello() {}

  public void method() {
    Object a = new Object();
    var b = new Object();
  }

  class Inner {
    public Inner(int a) { }
    public Inner() { }
  }
}
// xdoc section -- end

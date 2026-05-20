/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MatchXpath">
      <property name="query"
           value="//METHOD_DEF[./IDENT[@text='test' or @text='foo']]"/>
      <message key="matchxpath.match"
           value="Method name should not be 'test' or 'foo'"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

import java.util.HashMap;

// xdoc section -- start
public class Example3 {
  int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
  int[] small = {1, 2, 3};

  public Example3() { }
  public Example3(Object c) { }
  public Example3(int a, HashMap<String, Integer> b) { }

  public void method1() { }
  private void method2() { }
  public void method3() { }
  private void method4() { }
  public void method5() { }
  private void method6() { }

  // violation below 'Method name should not be test or foo'
  public void test() {}
  public void getName() {}
  // violation below 'Method name should not be test or foo'
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

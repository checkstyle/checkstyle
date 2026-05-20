/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MatchXpath">
      <property name="query"
           value="//CLASS_DEF[count(./OBJBLOCK/CTOR_DEF) > 1]"/>
      <message key="matchxpath.match"
           value="Classes with more than 1 constructor are not allowed"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

import java.util.HashMap;

// xdoc section -- start
public class Example5 {   // violation 'Classes with more than 1 constructor are not allowed'
  int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
  int[] small = {1, 2, 3};

  public Example5() { }
  public Example5(Object c) { }
  public Example5(int a, HashMap<String, Integer> b) { }

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

  // violation below 'Classes with more than 1 constructor are not allowed'
  class Inner {
    public Inner(int a) { }
    public Inner() { }
  }
}
// xdoc section -- end

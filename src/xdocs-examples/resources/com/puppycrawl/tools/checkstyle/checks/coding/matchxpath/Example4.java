/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MatchXpath">
      <property name="query"
           value="//VARIABLE_DEF[./ASSIGN/EXPR/LITERAL_NEW
                  and not(./TYPE/IDENT[@text='var'])]"/>
      <message key="matchxpath.match"
           value="New instances should be created via var keyword"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

import java.util.HashMap;

// xdoc section -- start
public class Example4 {
  int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
  int[] small = {1, 2, 3};

  public Example4() { }
  public Example4(Object c) { }
  public Example4(int a, HashMap<String, Integer> b) { }

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
    // violation below 'New instances should be created via var keyword'
    Object a = new Object();
    var b = new Object();
  }

  class Inner {
    public Inner(int a) { }
    public Inner() { }
  }
}
// xdoc section -- end

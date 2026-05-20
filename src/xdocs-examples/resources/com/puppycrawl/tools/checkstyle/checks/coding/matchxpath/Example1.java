/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="MatchXpath">
      <property name="query"
           value="//METHOD_DEF[.//LITERAL_PRIVATE
                  and following-sibling::METHOD_DEF[.//LITERAL_PUBLIC]]"/>
      <message key="matchxpath.match"
           value="Private methods must appear after public methods"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

import java.util.HashMap;

// xdoc section -- start
public class Example1 {
  int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
  int[] small = {1, 2, 3};

  public Example1() { }
  public Example1(Object c) { }
  public Example1(int a, HashMap<String, Integer> b) { }

  public void method1() { }
  // violation below 'Private methods must appear after public methods'
  private void method2() { }
  public void method3() { }
  // violation below 'Private methods must appear after public methods'
  private void method4() { }
  public void method5() { }
  private void method6() { }
  // violation above 'Private methods must appear after public methods'
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

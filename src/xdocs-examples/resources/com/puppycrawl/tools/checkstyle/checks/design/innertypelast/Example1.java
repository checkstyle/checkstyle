/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="InnerTypeLast"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.design.innertypelast;

// xdoc section -- start
class Test1 {
  private String s;
  class InnerTest1 {}
  public void test() {} // violation, method should be declared before inner types.
}

class Test2 {
  static {};
  class InnerTest1 {}
  public Test2() {} // violation, method should be declared before inner types.
}

class Example1 {
  private String s;
  public void test() {}
  class InnerTest1 {}
}
// xdoc section -- end

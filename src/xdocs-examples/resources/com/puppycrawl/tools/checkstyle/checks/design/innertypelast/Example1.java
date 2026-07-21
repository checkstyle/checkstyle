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
  // violation below, 'fields and methods should be before inner types'
  public void test() {}
}

class Test2 {
  static {};
  class InnerTest1 {}
  // violation below, 'fields and methods should be before inner types'
  public Test2() {}
}

class Example1 {
  private String s;
  public void test() {}
  class InnerTest1 {}
}
// xdoc section -- end

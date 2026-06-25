/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OverloadMethodsDeclarationOrder"/>
  </module>
</module>
*/


package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

// xdoc section -- start
class Example1 {
  void same(int i) {}
  void same(String s, int i, int k) {}
  // comments between overloaded methods are allowed.
  void same(String s) {}

  void same(int i, String s) {}

  void notSame() {}
  interface notSame{}

  void otherSame(String s) {}
  void foo() {}
  // violation 2 lines below """All overloaded methods should be placed next
  //  to each other. Previous overloaded method located at line '24'."""
  void otherSame(String s, int i) {}

  public enum ExampleEnum {
    VALUE_ONE,
    VALUE_TWO;

    public void example() {}
    public void example(String s, int i) {}

    void foo() {}

    // violation 2 lines below """All overloaded methods should be placed next
    //  to each other. Previous overloaded method located at line '35'."""
    public void example(String s) {}

  }
}
// xdoc section -- end

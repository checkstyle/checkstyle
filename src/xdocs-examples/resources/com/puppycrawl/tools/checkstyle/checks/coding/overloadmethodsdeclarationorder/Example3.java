/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OverloadMethodsDeclarationOrder">
      <property name="modifierGroups" value="static"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

// xdoc section - start
class Example3 {
  void same(int i) {}
  void same(String s, int i, int k) {}
  // comments between overloaded methods are allowed.
  void same(String s) {}

  void same(int i, String s) {}

  void notSame() {}
  interface notSame{}

  void otherSame(String s) {}
  void foo() {}
  // ok, static overloads form a separate group, so the method below
  // is not required to be next to the previous overload.
  static void otherSame(String s, int i) {}

  public enum ExampleEnum {
    VALUE_ONE,
    VALUE_TWO;

    public void example() {}
    public void example(String s, int i) {}

    void foo() {}

    // ok, static overloads form a separate group, so the method below
    // is not required to be next to the previous overload.
    public static void example(String s) {}

  }
}
// xdoc section - end

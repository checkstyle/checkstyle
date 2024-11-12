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
  // comments between overloaded methods are allowed.
  void same(String s) {}
  void same(String s, int i) {}
  void same(int i, String s) {}
  void notSame() {}
  interface notSame{}
}
// xdoc section -- end

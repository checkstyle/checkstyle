/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OverloadMethodsDeclarationOrder"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.overloadmethodsdeclarationorder;

// xdoc section -- start
class Example2 {
  void same(int i) {}
  // comments between overloaded methods are allowed.
  void same(String s) {}
  void notSame() {}
  // violation below, 'All overloaded methods should be placed next to each other'
  void same(int i, String s) {}
  interface notSame{}
  // violation below, 'All overloaded methods should be placed next to each other'
  void same(String s, int i) {}
}
// xdoc section -- end

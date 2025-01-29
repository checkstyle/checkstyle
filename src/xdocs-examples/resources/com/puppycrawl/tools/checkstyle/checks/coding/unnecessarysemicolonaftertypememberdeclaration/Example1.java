/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnnecessarySemicolonAfterTypeMemberDeclaration"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarysemicolonaftertypememberdeclaration;

// xdoc section -- start
class Example1 {
  ; // violation, 'Unnecessary semicolon'
  {}; // violation, 'Unnecessary semicolon'
  static {}; // violation, 'Unnecessary semicolon'
  Example1() {}; // violation, 'Unnecessary semicolon'
  void method() {}; // violation, 'Unnecessary semicolon'
  int field = 10;; // violation, 'Unnecessary semicolon'

  {
    ; //it is empty statement inside init block
  }

  static {
    ; //it is empty statement inside static init block
  }

  void anotherMethod() {
    ; //it is empty statement
    if (true) ; //it is empty statement
  }
}
// xdoc section -- end

/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RequireThis">
      <property name="validateOnlyOverlapping" value="false"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

// xdoc section -- start
class Example4 {
  int a,b,c;

  Example4(int a) {
    // overlapping by constructor argument
    this.a = a; // OK, no validation for fields
    b = 0; // violation, reference to instance variable "b" requires "this"
    foo(5); // violation, method call "foo(5)" requires "this"
  }

  void foo(int c) {
    // overlapping by method argument
    c = c; // violation, reference to instance variable "c" requires "this"
  }
}
// xdoc section -- end

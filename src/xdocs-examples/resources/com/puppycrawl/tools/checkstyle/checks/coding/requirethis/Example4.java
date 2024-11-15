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
  int field1,field2,field3;

  Example4(int field1) {
    this.field1 = field1;
    field2 = 0; // violation, reference to instance variable "field2" requires "this"
    foo(5); // violation, method call "foo(5)" requires "this"
  }

  void method2(int i) {
    foo(i); // violation, 'Method call to 'foo' needs "this.".'
  }

  void foo(int field3) {
    // violation below, reference to instance variable "field3" requires "this"
    field3 = field3;
  }
}
// xdoc section -- end

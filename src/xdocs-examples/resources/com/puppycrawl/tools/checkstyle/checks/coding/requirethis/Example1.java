/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RequireThis"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

// xdoc section -- start
class Example1 {
  int field1,field2,field3;

  Example1(int field1) {
    this.field1 = field1;
    field2 = 0;
    foo(5); // ok, methods cannot be overlapped in java.
  }

  void method2(int i) {
    foo(i); // ok, methods cannot be overlapped in java.
  }

  void foo(int field3) {
    // violation below, reference to instance variable "field3" requires "this"
    field3 = field3;
  }
}
// xdoc section -- end

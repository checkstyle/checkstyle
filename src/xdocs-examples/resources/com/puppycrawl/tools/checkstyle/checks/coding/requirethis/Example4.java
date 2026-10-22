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

// xdoc section - start
class Example4 {
  int field1,field2,field3;

  Example4(int field1) {
    this.field1 = field1;
    field2 = 0; // violation 'Reference to instance variable 'field2' needs "this.".'
    foo(5); // violation 'Method call to 'foo' needs "this.".'
  }

  void method2(int i) {
    foo(i); // violation 'Method call to 'foo' needs "this.".'
  }

  void foo(int field3) {
    // violation below 'Reference to instance variable 'field3' needs "this.".'
    field3 = field3;
  }
}
// xdoc section - end

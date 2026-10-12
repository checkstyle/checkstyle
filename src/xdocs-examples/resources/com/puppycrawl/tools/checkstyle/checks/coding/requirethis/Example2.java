/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RequireThis">
      <property name="validateOnlyOverlapping" value="false"/>
      <property name="checkMethods" value="false"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

// xdoc section - start
class Example2 {
  int field1,field2,field3;

  Example2(int field1) {
    this.field1 = field1;
    field2 = 0; // violation 'Reference to instance variable 'field2' needs "this.".'
    foo(5); // ok, checkMethods is false
  }

  void method2(int i) {
    foo(i); // ok, checkMethods is false
  }

  void foo(int field3) {
    // violation below 'Reference to instance variable 'field3' needs "this.".'
    field3 = field3;
  }
}
// xdoc section - end

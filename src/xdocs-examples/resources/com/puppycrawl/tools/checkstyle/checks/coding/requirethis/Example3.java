/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RequireThis">
      <property name="checkFields" value="false"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

// xdoc section -- start
class Example3 {
  int field1,field2,field3;

  Example3(int field1) {
    this.field1 = field1;
    field2 = 0;
    foo(5); // OK, methods cannot be overlapped in java.
  }

  void method2(int i) {
    foo(i); // OK, methods cannot be overlapped in java.
  }

  void foo(int field3) {

    field3 = field3;
  }
}
// xdoc section -- end

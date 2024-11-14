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
class Example5 {
  int field1,field2;

  public void foo(int field1) {
    field1 = this.field1;

    if (field1 > 0) {
      field1 = -field1;
    }
    // violation below, reference to instance variable "field2" requires "this"
    field2 *= field1;
  }
}
// xdoc section -- end

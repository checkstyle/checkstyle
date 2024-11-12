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
  int a,b;

  public void foo(int a) {
    a = this.a; // no violation

    if (a > 0) {
      a = -a; // no violation
    }
    b *= a; // violation, reference to instance variable "c" requires "this"
  }
}
// xdoc section -- end

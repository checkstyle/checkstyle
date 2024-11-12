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
  int a,b,c;

  Example3(int a) {
    // overlapping by constructor argument
    this.a = a; // OK, no validation for fields
    b = 0; // OK, no validation for fields
    foo(5); // OK, no overlap
  }

  void foo(int c) {
    // overlapping by method argument
    c = c; // OK, no validation for fields
  }
}
// xdoc section -- end

/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="NoArrayTrailingComma"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.noarraytrailingcomma;

// xdoc section -- start
class Example1 {
  void InvalidExample() {
    String[] foo1 = {
      "FOO",
      "BAR", // violation, 'Array should not contain trailing comma'
    };
    // violation below, 'Array should not contain trailing comma'
    String[] foo2 = { "FOO", "BAR", };
    String[] foo3 = {
      "FOO",
      "BAR"
    };
    String[] foo4 = { "FOO", "BAR" };
  }
}
// xdoc section -- end

/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DuplicateElseIfCondition"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.duplicateelseifcondition;

// xdoc section - start
class Example1 {
  void test(int x) {
    if (x > 0) {
      // do something
    } else if (x < 0) {
      // do something else
    } else if (x > 0) {
      // violation above """Duplicate condition,
      // identical to the condition at line '13'."""
    }

    if (x == 1) {
      // do something
    } else if (x == 2) {
      // do something
    } else if (x == 1) {
      // violation above """Duplicate condition,
      // identical to the condition at line '22'."""
    }
  }
}
// xdoc section - end

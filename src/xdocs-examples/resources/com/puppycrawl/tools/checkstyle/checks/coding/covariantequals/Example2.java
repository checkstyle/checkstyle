/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CovariantEquals"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.covariantequals;

// xdoc section -- start
public class Example2 {
  public boolean equals(Example2 i) {  // no violation
    return false;
  }

  public boolean equals(Object i) {
    return false;
  }
}
// xdoc section -- end

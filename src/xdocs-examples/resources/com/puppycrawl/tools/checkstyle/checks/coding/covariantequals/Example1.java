/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CovariantEquals"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.covariantequals;

// xdoc section -- start
public class Example1 {
  public boolean equals(Example1 i) {  // violation
    return false;
  }
}
// xdoc section -- end

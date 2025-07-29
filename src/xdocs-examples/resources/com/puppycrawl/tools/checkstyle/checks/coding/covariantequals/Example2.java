/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CovariantEquals"/>
  </module>
</module>
*/
// Java17
package com.puppycrawl.tools.checkstyle.checks.coding.covariantequals;

// xdoc section -- start
public class Example2 {
  public boolean equals(Example2 same) {  // no violation
    return false;
  }

  public boolean equals(Object o) {
    return false;
  }

  record Test(String str) {
    public boolean equals(Test same) {  // no violation
      return false;
    }

    public boolean equals(Object o) {
      return false;
    }
  }
}
// xdoc section -- end

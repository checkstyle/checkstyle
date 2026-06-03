/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EqualsAvoidNull">
      <property name="ignoreEqualsIgnoreCase" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

// xdoc section -- start
public class Example1 {
  public void foo() {
    String nullString = null;

    // violation 2 lines below """String literal expressions should
    //  be on the left side of an equals comparison."""
    nullString.equals("My_Sweet_String");
    "My_Sweet_String".equals(nullString);
    // violation 2 lines below """String literal expressions should
    //  be on the left side of an equalsIgnoreCase comparison."""
    nullString.equalsIgnoreCase("My_Sweet_String");
    "My_Sweet_String".equalsIgnoreCase(nullString);
  }
}
// xdoc section -- end

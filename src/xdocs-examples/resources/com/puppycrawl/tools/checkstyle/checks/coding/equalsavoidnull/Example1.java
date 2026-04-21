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

    // violation below 'String literal expressions .... equals comparison.'
    nullString.equals("My_Sweet_String");
    "My_Sweet_String".equals(nullString);
    // violation below 'String literal expressions .... equalsIgnoreCase comparison.'
    nullString.equalsIgnoreCase("My_Sweet_String");
    "My_Sweet_String".equalsIgnoreCase(nullString);
  }
}
// xdoc section -- end

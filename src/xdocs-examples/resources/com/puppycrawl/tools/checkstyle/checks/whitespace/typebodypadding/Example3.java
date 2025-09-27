/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeBodyPadding">
        <property name="starting" value="false"/>
        <property name="ending" value="true"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

// xdoc section -- start
public class Example3 {
  int a = 0;

  static class Inner {
    int b = 0;
  }
} // violation 'A blank line is required before the closing brace'
// xdoc section -- end

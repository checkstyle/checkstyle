/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeBodyPadding">
      <property name="skipInner" value="false"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

// xdoc section -- start
public class Example6 {
  int a = 0; // violation 'A blank line is required after the opening'
  static class Inner {
    int b = 0; // violation 'A blank line is required after the opening'
  }
}
// xdoc section -- end

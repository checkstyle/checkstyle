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
public class Example6 { // violation 'A blank line is required after the opening'
  int a = 0;
  static class Inner { // violation 'A blank line is required after the opening'
    int b = 0;
  }
}
// xdoc section -- end

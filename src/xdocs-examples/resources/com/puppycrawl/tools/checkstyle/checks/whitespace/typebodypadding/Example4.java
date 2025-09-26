/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeBodyPadding"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

// xdoc section -- start
public class Example4 { // violation 'A blank line is required after the opening'
  // Comments are not counted as blank lines.
  int a = 0;

  static class Inner {
    int b = 0;
  }
}
// xdoc section -- end

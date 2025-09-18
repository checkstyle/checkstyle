/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeBodyPadding"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

// xdoc section -- start
public class Example4 {
  // Comments are not counted as blank lines.
  int a = 0; // violation 'A blank line is required after the opening'
}
// xdoc section -- end

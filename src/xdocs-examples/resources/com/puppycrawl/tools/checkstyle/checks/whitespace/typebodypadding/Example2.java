/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeBodyPadding"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

// xdoc section -- start
public class Example2 {
  int a = 0; // violation 'A blank line is required after the opening'
}
// xdoc section -- end

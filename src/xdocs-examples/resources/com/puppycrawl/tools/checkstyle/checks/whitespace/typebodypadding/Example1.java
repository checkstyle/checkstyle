/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeBodyPadding">
        <property name="ending" value="true"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

// xdoc section -- start
public class Example1 {
  int a = 0; // violation 'A blank line is required after the opening'
} // violation 'A blank line is required before the closing'
// xdoc section -- end

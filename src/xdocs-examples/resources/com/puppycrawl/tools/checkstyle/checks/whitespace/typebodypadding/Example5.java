/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="TypeBodyPadding">
      <property name="ending" value="false"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

// xdoc section -- start
// violation below """Blank line required after the opening brace of
//  type definition."""
class Example5 {
  int x;
}

class Example5b {

  int x;
}
// xdoc section -- end

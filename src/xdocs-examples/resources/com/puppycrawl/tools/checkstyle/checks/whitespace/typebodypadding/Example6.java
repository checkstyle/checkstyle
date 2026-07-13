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
class Example6 {

  // violation below """Blank line required after the opening brace of
  //  type definition."""
  class Inner {
    int y;
  } // violation 'Blank line required before the closing brace of type definition.'

}
// xdoc section -- end

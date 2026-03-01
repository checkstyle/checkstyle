/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyLineWrappingInBlock">
      <property name="tokens" value="CLASS_DEF"/>
      <property name="topSeparator" value="empty_line"/>
      <property name="bottomSeparator" value="empty_line"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;
// xdoc section -- start
class Example3 { // violation 'Exactly one empty line is required after the opening brace.'
  private int x;
} // violation 'Exactly one empty line is required before the closing brace.'

class Ok {

  private int x;

}
// xdoc section -- end

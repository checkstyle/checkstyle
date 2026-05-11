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
// violation above ''{' must have exactly one empty line after.'
class Example3 {
  private int x;
} // violation ''}' must have exactly one empty line before'

class Ok {

  private int x;

}
// xdoc section -- end

/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyLineWrappingInBlock">
      <property name="topSeparator" value="empty_line_allowed"/>
      <property name="bottomSeparator" value="empty_line"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;
// xdoc section - start
class Example3 {
  private int field;

  void method() {
    int local = 1;

  }
} // violation ''}' must have exactly one empty line before.'
// xdoc section - end

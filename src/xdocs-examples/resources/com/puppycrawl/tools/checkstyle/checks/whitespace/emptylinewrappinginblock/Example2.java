/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyLineWrappingInBlock">
      <property name="topSeparator" value="empty_line"/>
      <property name="bottomSeparator" value="empty_line_allowed"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;
// xdoc section - start
class Example2 { // violation ''{' must have exactly one empty line after.'
  private int field;

  void method() {
    int local = 1;

  }
}
// xdoc section - end

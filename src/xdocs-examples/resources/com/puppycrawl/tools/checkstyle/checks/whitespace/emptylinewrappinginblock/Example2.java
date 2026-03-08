/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyLineWrappingInBlock">
      <property name="tokens" value="METHOD_DEF"/>
      <property name="topSeparator" value="empty_line_allowed"/>
      <property name="bottomSeparator" value="no_empty_line"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;
// xdoc section -- start
class Example2 {
  private void doSomething() {
    // method body
  }

  void ok() {
    doSomething();
  }

  void violation() {
    doSomething();

  } // violation ''}' can not have empty line before.'
}
// xdoc section -- end

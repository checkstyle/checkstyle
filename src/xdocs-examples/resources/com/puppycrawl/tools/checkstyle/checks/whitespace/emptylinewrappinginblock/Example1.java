/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="EmptyLineWrappingInBlock"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;
// xdoc section - start
class Example1 { // violation ''{' must have exactly one empty line after.'
  private int field;

  void method() {
    int local = 1;

  }
} // violation ''}' must have exactly one empty line before.'
// xdoc section - end

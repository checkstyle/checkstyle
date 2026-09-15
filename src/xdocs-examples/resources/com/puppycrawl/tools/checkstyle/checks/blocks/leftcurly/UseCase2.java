/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="LeftCurly">
      <property name="option" value="nl_or_singleline"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

// xdoc section - start
class UseCase2
{
  void singleLine() { doNothing(); }

  void multiLine()
  {
    doNothing();
  }

  // violation below ''{' at column 28 should be on a new line.'
  void trailingLeftCurly() {
    doNothing();
  }

  void doNothing() { }
}
// xdoc section - end

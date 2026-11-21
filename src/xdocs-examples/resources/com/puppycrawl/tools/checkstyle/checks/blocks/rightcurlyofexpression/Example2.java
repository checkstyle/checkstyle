/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurlyOfExpression">
      <property name="option" value="on_same_line"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyofexpression;

// xdoc section - start
class Example2 {

  String[] oneLine = new String[] {"One", }; // ok, single-line block

  String[] multiLine = new String[] {"One",
    "Two", }; // ok, right curly brace is on the same line

  String[] multiLineNewLine = new String[] {"One", "Two",
  }; // violation ''}' at column 3 should be on the previous line'

  Runnable action = () -> {
    doSomething();
  }; // violation ''}' at column 3 should be on the previous line'

  void doSomething() {
  }

  String[] extraLine = new String[] {"Three", "Four"}; // ok, single-line block
}
// xdoc section - end

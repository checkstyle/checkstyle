/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurlyOfExpression">
      <property name="option" value="on_new_line"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyofexpression;

// xdoc section - start
class Example3 {

  String[] oneLine = new String[] {"One", }; // violation 'should be on a new line'

  String[] multiLine = new String[] {"One",
    "Two", }; // violation ''}' at column 12 should be on a new line'

  String[] multiLineNewLine = new String[] {"One", "Two",
  }; // ok, right curly brace is on a new line

  Runnable action = () -> {
    doSomething();
  }; // ok, right curly brace is on a new line

  void doSomething() {
  }
}
// xdoc section - end

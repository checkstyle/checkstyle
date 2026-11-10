/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="RightCurlyOfExpression"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyofexpression;

// xdoc section - start
class Example1 {

  String[] oneLine = new String[] {"One", }; // ok, single-line block

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

/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OperatorWrap"/>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

// xdoc section -- start
class Example1 {
  void example() {
    String s = "Hello" + // violation ''\+' should be on a new line'
      "World";

    if (10 == // violation ''==' should be on a new line'
            20) {
    }

    if (10
            ==
            20) { }

    int c = 10 /
            5; // violation above ''/' should be on a new line'

    int d = c
            + 10;
  }
}
// xdoc section -- end

/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAround">
      <property name="allowEmptySwitchBlockStatements" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;
// xdoc section - start
class Example10 {
  int y = 0;
  void example() {
    switch (y) {
      case 1: {}
      // ok, allowEmptySwitchBlockStatements is true
    }
  }
}
// xdoc section - end

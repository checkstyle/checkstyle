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

// xdoc section -- start
class Example10 {
  int y = 0;
  void example() {
    switch (y) {
      case 1: {} // ok, empty switch block statement allowed
    }
  }
  void myFunction() {}
  // 2 violations above:
  //  ''{' is not followed by whitespace'
  //  ''}' is not preceded with whitespace'
}
// xdoc section -- end

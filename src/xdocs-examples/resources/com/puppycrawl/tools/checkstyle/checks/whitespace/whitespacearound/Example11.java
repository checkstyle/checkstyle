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
class Example11 {
  void example() {
    int i = 0;
    switch (i) {
      case 1: {}
    }

    int a=4; // 2 violations
    // ''=' is not preceded with whitespace.'
    // ''=' is not followed by whitespace.'
  }
}
// xdoc section -- end

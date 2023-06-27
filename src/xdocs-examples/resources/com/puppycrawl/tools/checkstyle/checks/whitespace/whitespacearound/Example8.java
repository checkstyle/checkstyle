/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAround">
      <property name="allowEmptyLambdas" value="true"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

// xdoc section -- start
class Example8 {
  void example() {
    Runnable noop = () -> {};
    int a=4; // 2 violations
    // no space before and after '='
  }
}
// xdoc section -- end

/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAround">
      <property name="allowEmptyCatches" value="true"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

// xdoc section -- start
class Example9 {
  void example() {
    int a=4; // 2 violations
    // no space before and after '='
    try {
    } catch (Exception e){}
  }
}
// xdoc section -- end

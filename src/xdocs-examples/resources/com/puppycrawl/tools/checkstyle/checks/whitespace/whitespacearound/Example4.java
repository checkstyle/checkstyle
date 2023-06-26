/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAround">
      <property name="allowEmptyMethods" value="true"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

// xdoc section -- start
class Example4 {
  public void muFunction() {}
  int a=4; // 2 violations
  // no space before and after '='
}
// xdoc section -- end

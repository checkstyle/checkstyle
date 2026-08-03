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
// xdoc section - start
class Example8 {
  void example() {
    try { }
    catch (Exception e){}
    // ok, allowEmptyCatches
    // is true above
  }
}
// xdoc section - end

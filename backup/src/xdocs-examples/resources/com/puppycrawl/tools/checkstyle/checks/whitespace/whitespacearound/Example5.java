/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAround">
      <property name="allowEmptyConstructors" value="true"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

// xdoc section -- start
class Example5 {
  public Example5() {}
  public void myFunction() {} // 2 violations
  // no space after '{', no space before '}'
}
// xdoc section -- end

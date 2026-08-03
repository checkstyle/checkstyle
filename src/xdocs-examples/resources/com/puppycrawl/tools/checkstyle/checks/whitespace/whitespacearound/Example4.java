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
// xdoc section - start
class Example4 {
  public Example4(){}
  // violation above ''{' is not preceded with whitespace.
  // ok, allowEmptyConstructors is true
}
// xdoc section - end

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
// xdoc section - start
class Example7 {
  void example() {
    Runnable noop = () ->{};
    // violation above '->' is not followed by whitespace.
    // ok, allowEmptyLambdas
    // is true above
  }
}
// xdoc section - end

/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAround">
      <property name="tokens" value="LCURLY, RCURLY, SLIST"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

// xdoc section -- start
class Example2 {
  interface Empty {}
  // 2 violations above:
  //  ''{' is not followed by whitespace'
  //  ''}' is not preceded with whitespace'
  int y = 0; // ok, '=' is not configured
  void example() {
    Runnable noop = () -> { }; // ok, '->' is not configured
    int a = 1 + 2; // ok, '=', '+' are not configured
  }
}
// xdoc section -- end

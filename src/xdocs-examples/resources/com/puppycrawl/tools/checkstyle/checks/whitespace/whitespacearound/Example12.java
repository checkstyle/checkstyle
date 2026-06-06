/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAround">
      <property name="allowEmptyInitializers" value="true"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

// xdoc section -- start
class Example12 {

  static {} int a; // ok, empty initializer
  static {int b = 1;} int c;
  // 2 violations above:
  // ''{' is not followed by whitespace'
  // ''}' is not preceded with whitespace'

  {} int d;

  void method() {
    int num = 1;
    {}   // ok, empty initializer

    {int left = 1;}
    // 2 violations above:
    // ''{' is not followed by whitespace'
    // ''}' is not preceded with whitespace'

  }
}
// xdoc section -- end

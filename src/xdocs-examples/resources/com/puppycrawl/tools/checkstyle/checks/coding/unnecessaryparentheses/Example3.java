/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnnecessaryParentheses">
      <property name="tokens" value="QUESTION" />
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

// xdoc section -- start
class Example3 {

  void method() {
    int a = 9, b = 8;
    int c = (a > b) ? 1 : 0; // violation 'Unnecessary parentheses around expression'

    int d = c == 1 ? (b % 2 == 0) ? 1 : 0 : 5;
    // violation above 'Unnecessary parentheses around expression'
  }

}
// xdoc section -- end

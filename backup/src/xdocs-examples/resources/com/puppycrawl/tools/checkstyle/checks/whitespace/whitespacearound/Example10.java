/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAround">
      <property name="ignoreEnhancedForColon" value="false"/>
    </module>
  </module>
</module>


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

// xdoc section -- start
class Example10 {
  void example() {
    int a=4; // 2 violations
    // no space before and after '='
    char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    for (char item: vowels) { // violation '':' is not preceded with whitespace'
    }
  }
}
// xdoc section -- end

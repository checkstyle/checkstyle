/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAround">
      <property name="allowEmptyLoops" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;
// xdoc section - start
class Example6 {
  int y = 0;
  void example() {
    char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    for (char item: vowels) { }
    for (int i = 0; i < 10; i++){}
    // ok, allowEmptyLoops
    // is true above
    do {} while (y == 1);
    // ok, allowEmptyLoops is true
  }
}
// xdoc section - end

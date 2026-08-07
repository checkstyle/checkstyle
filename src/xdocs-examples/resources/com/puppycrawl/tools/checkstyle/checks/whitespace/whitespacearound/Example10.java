/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAround">
      <property name="allowEmptySwitchBlockStatements" value="true"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;
// xdoc section - start
class Example10 {
  interface Empty {}
  // 2 violations above:
  //  ''{' is not followed by whitespace'
  //  ''}' is not preceded with whitespace'
  public Example10(){ }
  // violation above ''{' is not preceded with whitespace.'
  int y = 0;
  void example() {
    Runnable noop = () ->{ };
    // 2 violations above:
    //  ''->' is not followed by whitespace'
    //  ''{' is not preceded with whitespace'
    try { }
    catch (Exception e){ }
    // violation above ''{' is not preceded with whitespace.'
    char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    for (char item: vowels) { }
    do{ } while (y == 1);
    // 2 violations above:
    //  ''do' is not followed by whitespace'
    //  ''{' is not preceded with whitespace'
    switch (y) {
      case 1:{ }
      // ok, allowEmptySwitchBlockStatements is true
    }
  }
  void myFunction(){ }
  // violation above ''{' is not preceded with whitespace.'
}
// xdoc section - end

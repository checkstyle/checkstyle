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
// xdoc section - start
class Example2 {
  interface Empty {}
  // 2 violations above:
  //  ''{' is not followed by whitespace'
  //  ''}' is not preceded with whitespace'
  public Example2(){}
  // 3 violations above:
  //  ''{' is not followed by whitespace'
  //  ''{' is not preceded with whitespace'
  //  ''}' is not preceded with whitespace'
  int y = 0;
  void example() {
    Runnable noop = () ->{ };
    // violation above ''{' is not preceded with whitespace.'
    // ok, '->', '(' and ')'
    // are not configured
    try { }
    catch (Exception e){ }
    // violation above ''{' is not preceded with whitespace.'
    char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    for (char item: vowels) { }
    do {} while (y == 1);
    // 2 violations above:
    //  ''{' is not followed by whitespace'
    //  ''}' is not preceded with whitespace'
    switch (y) {
      case 1: {}
      // 2 violations above:
      //  ''{' is not followed by whitespace'
      //  ''}' is not preceded with whitespace'
    }
  }
  void myFunction() {}
  // 2 violations above:
  //  ''{' is not followed by whitespace'
  //  ''}' is not preceded with whitespace'
}
// xdoc section - end

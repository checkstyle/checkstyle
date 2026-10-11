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
// xdoc section - start
class Example9 {
  interface Empty{ }
  // violation above ''{' is not preceded with whitespace'
  public Example9() {}
  // 2 violations above:
  //  ''{' is not followed by whitespace'
  //  ''}' is not preceded with whitespace'
  int y = 0;
  void example() {
    Runnable noop = () ->{ };
    // 2 violations above:
    //  ''->' is not followed by whitespace'
    //  ''{' is not preceded with whitespace'
    try { }
    catch (Exception e){ }
    // violation above ''{' is not preceded with whitespace'
    char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    for (char item: vowels) { } // violation '':' is not preceded with whitespace'
    for (int i = 0; i < 10; i++){ }
    // violation above ''{' is not preceded with whitespace'
    switch (y) {
      case 1:{ }
      // violation above ''{' is not preceded with whitespace'
    }
  }
  void myFunction(){ }
  // violation above ''{' is not preceded with whitespace'
}
// xdoc section - end

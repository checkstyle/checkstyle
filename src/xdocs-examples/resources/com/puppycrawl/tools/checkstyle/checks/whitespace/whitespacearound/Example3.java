/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAround">
      <property name="tokens" value="LCURLY, RCURLY"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;
// xdoc section -- start
class Example3 {
  interface Empty {}
  // 2 violations above:
  //  ''{' is not followed by whitespace'
  //  ''}' is not preceded with whitespace'
  public Example3(){}
  // violation above ''}' is not preceded with whitespace'

  // ok, ')' is not in the tokens list

  int y = 0;
  void example() {
    Runnable noop = () ->{};
    // violation above ''}' is not preceded with whitespace'

    // ok, '->' is not in the tokens list

    // ok, ')' is not in the tokens list
    try { }
    catch (Exception e){}
    // violation above ''}' is not preceded with whitespace'

    // ok, ')' is not in the tokens list

    char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    for (char item: vowels) { }
    for (int i = 0; i < 10; i++){}
    // violation above ''}' is not preceded with whitespace'

    // ok, ')' is not in the tokens list

    do {} while (y == 1);
    // violation above ''}' is not preceded with whitespace'

    // ok, ')' is not in the tokens list
    switch (y) {
      case 1: {}
      // violation above ''}' is not preceded with whitespace'

      // ok, ':' is not in the tokens list
    }
  }
  void myFunction() {}
  // violation above ''}' is not preceded with whitespace'

  // ok, ')' is not in the tokens list
}
// xdoc section -- end

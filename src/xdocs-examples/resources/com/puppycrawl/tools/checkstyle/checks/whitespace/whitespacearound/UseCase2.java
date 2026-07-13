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
class UseCase2 {
  public UseCase2(){}
  // 3 violations above:
  // ''{' is not followed by whitespace'
  // ''{' is not preceded with whitespace'
  // ''}' is not preceded with whitespace'

  int y = 0;
  int a = 4;

  void example() {
    Runnable noop = () ->{};
    // 3 violations above:
    // ''{' is not followed by whitespace'
    // ''{' is not preceded with whitespace'
    // ''}' is not preceded with whitespace'

    char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    for (char item: vowels) { }
    for (int i = 100;i > 10; i--){}
    // 3 violations above:
    // ''{' is not followed by whitespace'
    // ''{' is not preceded with whitespace'
    // ''}' is not preceded with whitespace'

    int i = 0;
    switch (i) {
      case 1: {}
      // 2 violations above:
      // ''{' is not followed by whitespace'
      // ''}' is not preceded with whitespace'

    }
    int a=4;

  }

  void myFunction() {}
  // 2 violations above:
  // ''{' is not followed by whitespace'
  // ''}' is not preceded with whitespace'

}
// xdoc section -- end

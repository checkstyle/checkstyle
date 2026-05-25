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
  public Example3(){} // violation ''}' is not preceded with whitespace'

  int y = 0;
  int a = 4;

  void example() {
    Runnable noop = () ->{}; // violation ''}' is not preceded with whitespace'

    try { }
    catch (Exception e){} // violation ''}' is not preceded with whitespace'

    char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    for (char item: vowels) { }
    for (int i = 100;i > 10; i--){}
    // violation above ''}' is not preceded with whitespace'
    do {} while (y == 1); // violation ''}' is not preceded with whitespace'

    int i = 0;
    switch (i) {
      case 1: {} // violation ''}' is not preceded with whitespace'

    }
    int a=4;

  }

  void myFunction() {} // violation ''}' is not preceded with whitespace'

  void myFunction2() { }
}
// xdoc section -- end

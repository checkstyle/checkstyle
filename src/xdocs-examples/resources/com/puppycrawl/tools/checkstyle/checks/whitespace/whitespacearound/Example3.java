/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAround">
      <property name="tokens" value="LAMBDA"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

// xdoc section -- start
class Example3 {
  public Example3(){}

  int y = 0;
  int a = 4;

  void example() {
    Runnable noop = () ->{}; // violation ''->' is not followed by whitespace'

    try { }
    catch (Exception e){}

    char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    for (char item: vowels) { }
    for (int i = 100;i > 10; i--){}

    do {} while (y == 1);

    int i = 0;
    switch (i) {
      case 1: {}

    }
    int a=4;

  }

  void myFunction() {}

  void myFunction2() { }
}
// xdoc section -- end

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
// xdoc section -- start
class Example6 {
  interface Empty {}
  // 2 violations above:
  //  ''{' is not followed by whitespace'
  //  ''}' is not preceded with whitespace'
  public Example6(){}
  // 3 violations above:
  //  ''{' is not followed by whitespace'
  //  ''{' is not preceded with whitespace'
  //  ''}' is not preceded with whitespace'
  int y = 0;
  void example() {
    Runnable noop = () ->{};
    // 4 violations above:
    //  ''->' is not followed by whitespace'
    //  ''{' is not followed by whitespace'
    //  ''{' is not preceded with whitespace'
    //  ''}' is not preceded with whitespace'
    try { }
    catch (Exception e){}
    // 3 violations above:
    //  ''{' is not followed by whitespace'
    //  ''{' is not preceded with whitespace'
    //  ''}' is not preceded with whitespace'
    char[] vowels = {'a', 'e', 'i', 'o', 'u'};
    for (char item: vowels) { }
    for (int i = 0; i < 10; i++){}

    // ok, allowEmptyLoops
    // is true above

    do {} while (y == 1);

    // ok, allowEmptyLoops is true

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
// xdoc section -- end

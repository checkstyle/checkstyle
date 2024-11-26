/*xml
<module name="Checker">
  <module name="RegexpMultiline">
    <property name="format" value="System\.(out)|(err)\.print(ln)?\("/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

// xdoc section -- start
class Example2 {
  void testMethod1() {
    // violation below, 'Line matches the illegal pattern'
    System.out.print("Example");
    // violation below, 'Line matches the illegal pattern'
    System.err.println("Example");
    System
      .out.print("Example");
    System
      .err.println("Example"); // violation, 'Line matches the illegal pattern'
    System.
      out.print("Example");
    System.
      err.println("Example"); // violation, 'Line matches the illegal pattern'
  }

  void testMethod2() {
    // violation below, 'Line matches the illegal pattern'
    System.out.println("Test #1: this is a test string");
    // violation below, 'Line matches the illegal pattern'
    System.out.println("TeSt #2: This is a test string");
    // violation below, 'Line matches the illegal pattern'
    System.out.println("TEST #3: This is a test string");
    int i = 5;
    // violation below, 'Line matches the illegal pattern'
    System.out.println("Value of i: " + i);
    // violation below, 'Line matches the illegal pattern'
    System.out.println("Test #4: This is a test string");
    // violation below, 'Line matches the illegal pattern'
    System.out.println("TEst #5: This is a test string");
  }
}
// xdoc section -- end

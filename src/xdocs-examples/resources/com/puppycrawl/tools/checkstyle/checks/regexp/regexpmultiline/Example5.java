/*xml
<module name="Checker">
  <module name="RegexpMultiline">
    <property name="format" value="Test #[0-9]+:[A-Za-z ]+"/>
    <property name="minimum" value="2"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.regexp.regexpmultiline;

// xdoc section -- start
class Example5 {
  void testMethod1() {

    System.out.print("Example");

    System.err.println("Example");
    System
    .out.print("Example");
    System
    .err.println("Example");
    System.
    out.print("Example");
    System.
    err.println("Example");
  }

  void testMethod2() {
    // violation below, 'Line matches the illegal pattern'
    System.out.println("Test #1: this is a test string");

    System.out.println("TEST #2: This is a test string");

    System.out.println("TEST #3: This is a test string");
    int i = 5;

    System.out.println("Value of i: " + i);
    // violation below, 'Line matches the illegal pattern'
    System.out.println("Test #3: This is a test string");
    // violation below, 'Line matches the illegal pattern'
    System.out.println("Test #4: This is a test string");
  }
}
// xdoc section -- end
